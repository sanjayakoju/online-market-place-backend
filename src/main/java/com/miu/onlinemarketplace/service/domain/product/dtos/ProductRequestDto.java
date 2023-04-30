package com.miu.onlinemarketplace.service.domain.product.dtos;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDto {

    private Long productId;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Boolean isVerified;
    private Boolean isDeleted;
    private VendorDto vendor;
    private ProductCategoryDto productCategory;
}
