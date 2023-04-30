package com.miu.onlinemarketplace.service.domain.users.dtos;

import lombok.Data;

@Data
public class SignupEmailVerificationRequestDTO {
    private String email;

    private String emailVerificationCode;
}
