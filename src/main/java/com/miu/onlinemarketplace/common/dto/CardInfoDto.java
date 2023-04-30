package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfoDto {

    private Long cardInfoId;
    private String cardNumber;
    private String cardDigit;
    private String nameOnCard;
    private Integer expYear;
    private Integer expMonth;
    private String cvc;
    private String cardBrand;
//    private String addressType;


}
