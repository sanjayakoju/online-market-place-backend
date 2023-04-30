package com.miu.onlinemarketplace.service.domain.users;

import com.miu.onlinemarketplace.common.dto.SignUpMailSenderDto;
import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.common.enums.UserStatus;
import com.miu.onlinemarketplace.config.AppProperties;
import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.exception.CustomAppException;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.RoleRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.repository.VendorRepository;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.domain.users.dtos.VendorRegistrationRequest;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import com.miu.onlinemarketplace.service.payment.StripePaymentService;
import com.miu.onlinemarketplace.utils.GenerateRandom;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final AppProperties appProperties;
    private final EmailSenderService emailSenderService;

    private final StripePaymentService paymentProvider;

    @Override
    public Page<VendorDto> getAllVendors(Pageable pageable) {
        Page<VendorDto> vendorDtoPage = vendorRepository.findAll(pageable).map(vendor -> {
            VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class);
            User user = vendor.getUser() != null ? vendor.getUser() : new User();
            vendorDto.setUserDto(modelMapper.map(user, UserDto.class));
            return vendorDto;
        });
        return vendorDtoPage;
    }

    @Override
    public VendorDto getVendorById(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new DataNotFoundException("Vendor not found"));
        VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class);
        vendorDto.setUserDto(modelMapper.map(vendorDto.getUserDto(), UserDto.class));
        return vendorDto;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public VendorDto registerVendor(VendorRegistrationRequest vendorRegistrationRequest) {

        // Save as a user
        User user = new User();
        user.setEmail(vendorRegistrationRequest.getEmail());
        user.setFullName(vendorRegistrationRequest.getCompanyName());
        user.setPassword(passwordEncoder.encode(vendorRegistrationRequest.getPassword()));
        Role vendorRole = roleRepository.findOneByRole(EnumRole.ROLE_VENDOR);
        user.setRole(vendorRole);

        String signUpPasswordVerCode = GenerateRandom.generateRandomAlphaNumericString(20);
        long verificationCodeExpirationSeconds = appProperties.getMail().getVerificationCodeExpirationSeconds();
        user.setEmailVerificationCodeExpiresAt(LocalDateTime.now().plusSeconds(verificationCodeExpirationSeconds));
        user.setEmailVerificationCode(signUpPasswordVerCode);
        user.setUserStatus(UserStatus.UNVERIFIED);

        User returnedUser = userRepository.save(user);

        // Save to Vendor
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorRegistrationRequest.getCompanyName());
        vendor.setDescription(vendorRegistrationRequest.getDescription());
        vendor.setUser(returnedUser);
        vendorRepository.save(vendor);

        // Vendor payment
        double amount = 20000; // VendorType.GLOBAL

        if (paymentProvider.pay("", amount) == null) {
            throw new CustomAppException("Payment failed, please retry again or use different card");
        }

        // Sign up verification email
        SignUpMailSenderDto signUpMailSenderDto = new SignUpMailSenderDto();
        signUpMailSenderDto.setToEmail(user.getEmail());
        signUpMailSenderDto.setVerificationCode(signUpPasswordVerCode);
        emailSenderService.sendSignupMail(signUpMailSenderDto);

        VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class);
        vendorDto.setUserDto(modelMapper.map(vendor.getUser(), UserDto.class));
        return vendorDto;
    }

    @Override
    public VendorDto verifyVendor(VendorDto vendorDto) {
        Vendor vendor = modelMapper.map(vendorDto, Vendor.class);
        User user = userRepository.findById(vendor.getUser().getUserId())
                .orElseThrow(() -> new CustomAppException("Vendor doesn't have linked userId"));
        user.setUserStatus(UserStatus.ACTIVE);
        Vendor updatedVendor = vendorRepository.save(vendor);
        return modelMapper.map(updatedVendor, VendorDto.class);
    }

    @Override
    public Page<VendorDto> filterVendorData(GenericFilterRequestDTO<VendorDto> genericFilterRequest, Pageable pageable) {

        Specification<Vendor> specification = Specification
                .where(VendorSearchSpecification.processDynamicVendorFilter(genericFilterRequest));
        Page<VendorDto> filteredVendorPage = vendorRepository.findAll(specification, pageable).map(vendor -> {
            VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class);
            User user = vendor.getUser() != null ? vendor.getUser() : new User();
            vendorDto.setUserDto(modelMapper.map(user, UserDto.class));
            return vendorDto;
        });
        return filteredVendorPage;
    }


}
