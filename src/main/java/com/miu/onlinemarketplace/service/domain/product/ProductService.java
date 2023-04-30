package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.service.domain.product.dtos.ProductRequestDto;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    Page<ProductResponseDto> getCustomerProducts(Pageable pageable, Long categoryId);

    Page<ProductResponseDto> getAllProducts(Pageable pageable, Long categoryId);

    Page<ProductResponseDto> getAllProductsOfVendor(Pageable pageable);

    Page<ProductDto> getProductByName(Pageable pageable, String name);

    ProductResponseDto getProductByProductId(Long id);

    ProductResponseDto getByProductId(Long id);

    ProductDto createNewProduct(List<MultipartFile> files, ProductDto productDto);

    ProductResponseDto verifyProduct(Long productId, boolean isVerified);

    ProductDto updateProduct(ProductDto productDto);

    Boolean deleteProduct(Long productId);

    Page<ProductResponseDto> filterProductData(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest, Pageable pageable);

}
