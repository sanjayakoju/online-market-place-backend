package com.miu.onlinemarketplace.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import com.miu.onlinemarketplace.common.enums.ShippingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CheckingOrderDto {
    private Long orderId;
    private OrderStatus orderStatus;
    private String orderCode;
    private double total;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItemDto;
    private ShippingStatus shippingStatus;
}
