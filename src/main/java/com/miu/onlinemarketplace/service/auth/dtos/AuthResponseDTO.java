package com.miu.onlinemarketplace.service.auth.dtos;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String fullName;
    private Long userId;
    private String token;
    private String role;
    private String username;
    private String email;
}
