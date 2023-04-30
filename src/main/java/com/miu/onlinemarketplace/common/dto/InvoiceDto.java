package com.miu.onlinemarketplace.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private String paidBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderPlaced;
    private String orderCode;
    private OrderStatus orderStatus;
    private double totalBeforeTax;
    private double tax;
    private double totalAfterTax;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shippedDate;
    private List<OrderItemDto> orderItemList;
    private ShippingAddressDto shippingAddress;
    private List<PaymentDto> paymentMethod;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
