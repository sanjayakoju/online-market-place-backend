package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.auth.AuthenticationService;
import com.miu.onlinemarketplace.service.auth.dtos.AuthResponseDTO;
import com.miu.onlinemarketplace.service.auth.dtos.LoginRequestDTO;
import com.miu.onlinemarketplace.service.auth.dtos.RegisterUserRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.VendorService;
import com.miu.onlinemarketplace.service.domain.users.dtos.ForgotPasswordRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.dtos.PasswordResetVerRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.dtos.SignupEmailVerificationRequestDTO;
import com.miu.onlinemarketplace.service.domain.users.dtos.VendorRegistrationRequest;
import com.miu.onlinemarketplace.service.generic.dtos.GenericResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final VendorService vendorService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Authentication API: login requested by user: ", loginRequest.getUsername());
        AuthResponseDTO authResponseDTO = authenticationService.loginUser(loginRequest);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        log.info("Authentication API: registerUser: ", registerUserRequestDTO.getEmail());
        UserDto userDTO = authenticationService.createUser(registerUserRequestDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/register-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody VendorRegistrationRequest vendorRegistrationRequest) {
        log.info("Vendor API: registerVendor: ", vendorRegistrationRequest);
        VendorDto vendorDTO = vendorService.registerVendor(vendorRegistrationRequest);
        return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
    }

    @GetMapping("/check-email-availability")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email) {
        log.info("Vendor API: check email availability: ", email);
        GenericResponseDTO<Boolean> checkEmailAvailability = authenticationService.checkEmailAvailability(email);
        return new ResponseEntity<>(checkEmailAvailability, HttpStatus.OK);
    }

    @PostMapping("/check-email-verification-code")
    public ResponseEntity<?> verifySignUpEmail(@RequestBody SignupEmailVerificationRequestDTO signupEmailVerificationRequestDTO) {
        log.info("Authentication API: verifySignUpEmail: ", signupEmailVerificationRequestDTO.getEmail());
        GenericResponseDTO<Boolean> resendVerificationEmailStatus = authenticationService.verifySignUpEmail(signupEmailVerificationRequestDTO);
        return new ResponseEntity<>(resendVerificationEmailStatus, HttpStatus.OK);
    }

    @PostMapping("/send-forgot-password-email")
    public ResponseEntity<?> sendForgotPasswordResetEmail(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        log.info("Authentication API: sendForgotPasswordResetEmail: ", forgotPasswordRequestDTO.getEmail());
        GenericResponseDTO<Boolean> resendVerificationEmailStatus = authenticationService.sendForgotPasswordResetEmail(forgotPasswordRequestDTO);
        return new ResponseEntity<>(resendVerificationEmailStatus, HttpStatus.OK);
    }

    @PostMapping("/process-password-update-request")
    public ResponseEntity<?> verifyAndProcessPasswordResetVerRequest(@RequestBody PasswordResetVerRequestDTO passwordResetVerRequestDTO) {
        log.info("Authentication API: verifyAndProcessPasswordResetVerRequest: ", passwordResetVerRequestDTO.getEmail());
        GenericResponseDTO<Boolean> checkVerificationCodeStatus = authenticationService.verifyAndProcessPasswordResetVerRequest(passwordResetVerRequestDTO);
        return new ResponseEntity<>(checkVerificationCodeStatus, HttpStatus.OK);
    }


}