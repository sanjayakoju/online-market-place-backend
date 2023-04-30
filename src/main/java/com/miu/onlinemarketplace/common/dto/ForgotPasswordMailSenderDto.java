package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordMailSenderDto {
    private String toEmail;
    private String verificationCode;
}
