package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.entities.ProductTemp;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.service.domain.product.dtos.ProductRequestDto;
import com.miu.onlinemarketplace.service.generic.GenericSpecification;
import com.miu.onlinemarketplace.service.generic.SearchCriteria;
import com.miu.onlinemarketplace.service.generic.SearchOperation;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class ProductTempSearchSpecification {

    public static GenericSpecification<ProductTemp> processDynamicProductTempFilter(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest) {
        GenericSpecification<ProductTemp> dynamicUserSpec = new GenericSpecification<ProductTemp>();
        ProductRequestDto productFilter = genericFilterRequest.getDataFilter();
        if (productFilter.getProductId() != null && productFilter.getProductId() > 0) {
            dynamicUserSpec.add(new SearchCriteria("productId", productFilter.getProductId(), SearchOperation.EQUAL));
        }
        if (productFilter.getName() != null) {
            dynamicUserSpec.add(new SearchCriteria("name", productFilter.getName(), SearchOperation.MATCH));
        }
        return dynamicUserSpec;
    }

    public static Specification<ProductTemp> getProductByVendor(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest) {
        Specification<ProductTemp> vendorEntitySpecification = new GenericSpecification<>();
        Long vendorId = Optional.ofNullable(genericFilterRequest)
                .map(GenericFilterRequestDTO<ProductRequestDto>::getDataFilter)
                .map(ProductRequestDto::getVendor)
                .map(VendorDto::getVendorId)
                .orElse(null);
        if (vendorId != null) {
            vendorEntitySpecification = getProductSpecificationByVendorId(vendorId);
        }
        return vendorEntitySpecification;
    }

    private static Specification<ProductTemp> getProductSpecificationByVendorId(Long vendorId) {
        Specification<ProductTemp> vendorEntitySpecification;
        vendorEntitySpecification = (root, query, criteriaBuilder) -> {
            Join<ProductTemp, Vendor> vendorJoin = root.join("vendor");
            Predicate equalPredicate = criteriaBuilder.equal(vendorJoin.get("vendorId"), vendorId);
            query.distinct(true);
            return equalPredicate;
        };
        return vendorEntitySpecification;
    }

    public static Specification<ProductTemp> getProductByCategory(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest) {
        Specification<ProductTemp> categoryEntitySpecification = new GenericSpecification<>();
        Long categoryId = Optional.ofNullable(genericFilterRequest)
                .map(GenericFilterRequestDTO<ProductRequestDto>::getDataFilter)
                .map(ProductRequestDto::getProductCategory)
                .map(ProductCategoryDto::getCategoryId)
                .orElse(null);
        if (categoryId != null) {
            categoryEntitySpecification = getProductSpecificationByCategoryId(categoryId);
        }
        return categoryEntitySpecification;
    }

    @NotNull
    private static Specification<ProductTemp> getProductSpecificationByCategoryId(Long categoryId) {
        Specification<ProductTemp> categoryEntitySpecification;
        categoryEntitySpecification = (root, query, criteriaBuilder) -> {
            Join<ProductTemp, ProductCategory> vendorJoin = root.join("category");
            Predicate equalPredicate = criteriaBuilder.equal(vendorJoin.get("categoryId"), categoryId);
            query.distinct(true);
            return equalPredicate;
        };
        return categoryEntitySpecification;
    }

}
