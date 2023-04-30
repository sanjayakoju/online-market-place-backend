package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private OrderDto orderDto;
    private List<OrderItemDto> relatedOrderItems;
    private double total;
}
