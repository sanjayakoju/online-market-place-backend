package com.miu.onlinemarketplace.service.domain.users.dtos;

import lombok.Data;

@Data
public class ForgotPasswordRequestDTO {
    private String email;
}