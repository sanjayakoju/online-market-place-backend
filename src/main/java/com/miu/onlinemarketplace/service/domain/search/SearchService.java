package com.miu.onlinemarketplace.service.domain.search;

import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    Page<ProductResponseDto> advanceSearch(String name, String categoryName, Double minPrice, Double maxPrice, String sortPrice, Pageable pageable);
}
