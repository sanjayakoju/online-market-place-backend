package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendorProductReportDto {
    private Integer id;
    private String productName;
    private Double quantity;
    private Double rate;
    private Double subTotal;
    private Double discount;
    private Double tax;
    private Double total;
}
