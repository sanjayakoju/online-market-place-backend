package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPayInfoDto {

    private Long userId;
    private String fullName;

    private List<AddressDto> addressDtos;
    private List<CardInfoDto> cardInfoDtos;

    private double itemPrice;
    private Integer quantity;
    private double price;

}
