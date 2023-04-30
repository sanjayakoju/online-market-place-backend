package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ModelMapper modelMapper;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl( final ModelMapper modelMapper, final ProductCategoryRepository productCategoryRepository) {
        this.modelMapper = modelMapper;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategoryDto> getAllCategory() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        return productCategories.stream()
                .map(productCategory -> modelMapper.map(productCategory, ProductCategoryDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public ProductCategoryDto createCategory(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = modelMapper.map(productCategoryDto, ProductCategory.class);
        ProductCategory savedProduct = productCategoryRepository.save(productCategory);
        return modelMapper.map(savedProduct, ProductCategoryDto.class);
    }

    @Override
    public ProductCategoryDto updateCategory(ProductCategoryDto category) {
        ProductCategory productCategory = productCategoryRepository.findById(category.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not Found"));
        productCategory.setCategory(category.getCategory());
        ProductCategory savedCategory = productCategoryRepository.save(productCategory);
        return modelMapper.map(savedCategory, ProductCategoryDto.class);
    }

    @Override
    public ProductCategoryDto deleteCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        productCategoryRepository.delete(productCategory);
        return modelMapper.map(productCategory, ProductCategoryDto.class);
    }
}
