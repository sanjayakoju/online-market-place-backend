package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMailSenderDto {
    private String fullName;
    private String toEmail;
    private String orderCode;
    private String orderUrl;
    private OrderStatus orderStatus;
}
