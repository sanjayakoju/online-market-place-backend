package com.miu.onlinemarketplace.service.payment.dtos;

import lombok.Data;

@Data
public class TransactionResponseDto {

    private String transactionId;
    private double amount;
    private boolean isPaid;

}
