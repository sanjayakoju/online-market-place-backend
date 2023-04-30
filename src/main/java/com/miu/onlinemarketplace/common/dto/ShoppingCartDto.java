package com.miu.onlinemarketplace.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDto {

    private Long cartId;
    private Integer quantity;

    private UserDto user;

    private ProductResponseDto product;
}
