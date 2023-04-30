package com.miu.onlinemarketplace.common.dto;

import lombok.Data;

@Data
public class CardInfoRequest {
    private String cardNumber;
    private String firstName;
    private String lastName;
    private String expiryDate;
    private String cvv;
}
