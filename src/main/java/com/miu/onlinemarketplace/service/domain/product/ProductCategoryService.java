package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryDto> getAllCategory();
    ProductCategoryDto createCategory(ProductCategoryDto productCategory);
    ProductCategoryDto updateCategory(ProductCategoryDto productCategory);
    ProductCategoryDto deleteCategory(Long categoryId);
}
