package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private String cardNumber;
    private String cardHolderName;
    private Double payAmount;
    private String cardBrand;
    private String transactionId;
    private AddressDto addressDto;
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
