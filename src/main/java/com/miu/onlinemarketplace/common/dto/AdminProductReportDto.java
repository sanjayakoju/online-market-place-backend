package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminProductReportDto {
    private Integer id;
    private String productName;
    private Double quantity;
    private Double totalRevenue;
    private Double avgPrice;
    private Double commission;
    private Double tax;
}
