package com.miu.onlinemarketplace.service.domain.users.dtos;

import lombok.Data;

@Data
public class PasswordResetVerRequestDTO {

    private String email;

    private String forgotPasswordVerCode;

    private String newPassword;
}
