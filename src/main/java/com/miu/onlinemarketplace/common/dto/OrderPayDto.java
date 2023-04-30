package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.CardBrand;
import com.miu.onlinemarketplace.common.enums.OrderPayStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayDto {

    // Authenticated USer
    private AddressDto addressDto;
    private CardInfoDto cardInfoDto;

    // Guest User
    private Boolean isGuestUser;
    private String clientIp;
    private String cardId;
//    private OrderPayStatus status;
    private String transactionId;

    private Long userId;
    private String fullName;
    private String email;
    private Integer quantity;
    private double price;
    private List<ShoppingCartDto> shoppingCartDtos;


}
