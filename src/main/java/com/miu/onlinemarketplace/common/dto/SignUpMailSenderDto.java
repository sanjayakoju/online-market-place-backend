package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpMailSenderDto {
    private String toEmail;
    private String verificationCode;
}
