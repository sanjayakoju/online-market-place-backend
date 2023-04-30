package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {

    private Long productId;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Boolean isVerified;
    private Boolean isDeleted;
    private VendorDto vendor;
    private ProductCategoryDto productCategory;
    private List<FileDto> images;

}
