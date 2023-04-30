package com.miu.onlinemarketplace.service.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.miu.onlinemarketplace.common.dto.ForgotPasswordMailSenderDto;
import com.miu.onlinemarketplace.common.dto.SignUpMailSenderDto;
import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.enums.UserStatus;
import com.miu.onlinemarketplace.config.AppProperties;
import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.RoleRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.security.JwtTokenProvider;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.auth.dtos.AuthResponseDTO;
import com.miu.onlinemarketplace.service.auth.dtos.LoginRequestDTO;
import com.miu.onlinemarketplace.service.auth.dtos.RegisterUserRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.dtos.ForgotPasswordRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.dtos.PasswordResetVerRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.dtos.SignupEmailVerificationRequestDTO;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericResponseDTO;
import com.miu.onlinemarketplace.utils.GenerateRandom;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    private final AppProperties appProperties;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponseDTO loginUser(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = jwtTokenProvider.createToken(authentication);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            User user = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new DataNotFoundException("Error, while fetching user"));
            if (user.getUserStatus().equals(UserStatus.ACTIVE)) {
                authResponseDTO.setUserId(user.getUserId());
                authResponseDTO.setFullName(user.getFullName());
                authResponseDTO.setUsername(user.getEmail());
                authResponseDTO.setEmail(user.getEmail());
                authResponseDTO.setRole(user.getRole().getRole().getValue());
                authResponseDTO.setToken(token);
                return authResponseDTO;
            }
            if (user.getUserStatus().equals(UserStatus.UNVERIFIED)) {
                throw new BadCredentialsException("Your email is not Verified. Please Verify your email!");
            } else if (user.getUserStatus().equals(UserStatus.SUSPENDED)) {
                throw new BadCredentialsException("Your Account is not available, Contact Support !");
            } else if (user.getRole().getRole().equals(EnumRole.ROLE_VENDOR) && user.getUserStatus().equals(UserStatus.VERIFIED)) {
                throw new BadCredentialsException("Account not yet active, Please wait for an Admin approval !");
            } else {
                throw new BadCredentialsException("Couldn't generate AuthResponse, UserStatus is not valid");
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto createUser(RegisterUserRequestDTO registerUserRequestDTO) {
        User user = new User();
        user.setEmail(registerUserRequestDTO.getEmail());
        user.setFullName(registerUserRequestDTO.getFullName());
        user.setPassword(passwordEncoder.encode(registerUserRequestDTO.getPassword()));
        Role userRole = roleRepository.findOneByRole(EnumRole.ROLE_USER);
        user.setRole(userRole);
        user.setUserStatus(UserStatus.UNVERIFIED);

        String signUpPasswordVerCode = GenerateRandom.generateRandomAlphaNumericString(20);
        long verificationCodeExpirationSeconds = appProperties.getMail().getVerificationCodeExpirationSeconds();
        user.setEmailVerificationCodeExpiresAt(LocalDateTime.now().plusSeconds(verificationCodeExpirationSeconds));
        user.setEmailVerificationCode(signUpPasswordVerCode);
        User returnedUser = userRepository.save(user);

        // Sign up verification email
        SignUpMailSenderDto signUpMailSenderDto = new SignUpMailSenderDto();
        signUpMailSenderDto.setToEmail(user.getEmail());
        signUpMailSenderDto.setVerificationCode(signUpPasswordVerCode);
        emailSenderService.sendSignupMail(signUpMailSenderDto);
        return modelMapper.map(returnedUser, UserDto.class);
    }

    @Override
    public GenericResponseDTO<Boolean> checkEmailAvailability(String email) {
        boolean exists = userRepository.existsByEmail(email);
        return new GenericResponseDTO<>(!exists, "");
    }

    @Override
    public GenericResponseDTO<Boolean> verifySignUpEmail(SignupEmailVerificationRequestDTO signupEmailVerificationRequestDTO) {
        User user = userRepository.verifySignUpEmailByVerificationCode(
                        signupEmailVerificationRequestDTO.getEmail(), signupEmailVerificationRequestDTO.getEmailVerificationCode())
                .orElseThrow(() -> new DataNotFoundException("Invalid request - Email or VerificationCode is not valid"));
        user.setVerificationCodeExpiresAt(null);
        user.setVerificationCode(null);
        if (user.getRole().getRole().equals(EnumRole.ROLE_USER)) {
            user.setUserStatus(UserStatus.ACTIVE);
        } else {
            // For vendor, Only Verified, once the admin approves, than only user is ACTIVE
            user.setUserStatus(UserStatus.VERIFIED);
        }
        userRepository.save(user);
        GenericResponseDTO<Boolean> emailVerifiedResponseDTO = GenericResponseDTO.<Boolean>builder().response(true).build();
        return emailVerifiedResponseDTO;
    }


//    private UserDto mapUserToUserDTO(User user) {
//        UserDto userDTO = new UserDto();
//        userDTO.setId(user.getUserId());
//        userDTO.setEmail(user.getEmail());
//        return userDTO;
//    }

    @Override
    public GenericResponseDTO<Boolean> sendForgotPasswordResetEmail(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        User user = userRepository.findByEmail(forgotPasswordRequestDTO.getEmail())
                .orElseThrow(() -> new DataNotFoundException("Invalid request, this email is not registered"));
        String forgotPasswordVerCode = GenerateRandom.generateRandomAlphaNumericString(20);
        long verificationCodeExpirationSeconds = appProperties.getMail().getVerificationCodeExpirationSeconds();
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusSeconds(verificationCodeExpirationSeconds));
        user.setVerificationCode(forgotPasswordVerCode);
        userRepository.save(user);

        // Sending password reset link
        ForgotPasswordMailSenderDto forgotPasswordMailSenderDto = new ForgotPasswordMailSenderDto();
        forgotPasswordMailSenderDto.setToEmail(user.getEmail());
        forgotPasswordMailSenderDto.setVerificationCode(forgotPasswordVerCode);
        Boolean isEmailSent = emailSenderService.sendForgotPasswordMail(forgotPasswordMailSenderDto);
        GenericResponseDTO<Boolean> genericResponseDTO = GenericResponseDTO.<Boolean>builder().response(isEmailSent).build();
        return genericResponseDTO;
    }

    @Override
    public GenericResponseDTO<Boolean> verifyAndProcessPasswordResetVerRequest(PasswordResetVerRequestDTO passwordResetVerRequestDTO) {
        User user = userRepository.verifyPasswordResetVerificationRequest(
                        passwordResetVerRequestDTO.getEmail(), passwordResetVerRequestDTO.getForgotPasswordVerCode())
                .orElseThrow(() -> new DataNotFoundException("Invalid request - Email or VerificationCode is not valid"));
        user.setVerificationCodeExpiresAt(null);
        user.setVerificationCode(null);
        user.setPassword(passwordEncoder.encode(passwordResetVerRequestDTO.getNewPassword()));
        userRepository.save(user);
        GenericResponseDTO<Boolean> emailVerifiedResponseDTO = GenericResponseDTO.<Boolean>builder().response(true).build();
        return emailVerifiedResponseDTO;
    }

}
