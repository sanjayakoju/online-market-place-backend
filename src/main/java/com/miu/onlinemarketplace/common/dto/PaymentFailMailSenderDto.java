package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentFailMailSenderDto {
    private String fullName;
    private String toEmail;
    private String orderCode;
}
