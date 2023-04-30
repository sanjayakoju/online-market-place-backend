package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTempDto {

    private Long productId;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Boolean isVerified;
    private Boolean isDeleted;
    private Long vendorId;
    private Long categoryId;
}
