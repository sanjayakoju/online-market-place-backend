package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.OrderItemStatus;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long orderItemId;
    private Double price;
    private Double tax;
    private Integer quantity;
    private Double discount;
    private Boolean isCommissioned;
    private ProductDto product;
    private OrderDto order;
    private OrderItemStatus orderItemStatus;
}
