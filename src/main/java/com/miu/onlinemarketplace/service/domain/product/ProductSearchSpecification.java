package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.ProductCategory;
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

public class ProductSearchSpecification {

    public static GenericSpecification<Product> processDynamicProductFilter(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest) {
        GenericSpecification<Product> dynamicUserSpec = new GenericSpecification<Product>();
        ProductRequestDto productFilter = genericFilterRequest.getDataFilter();
        if (productFilter.getProductId() != null && productFilter.getProductId() > 0) {
            dynamicUserSpec.add(new SearchCriteria("id", productFilter.getProductId(), SearchOperation.EQUAL));
        }
        if (productFilter.getName() != null) {
            dynamicUserSpec.add(new SearchCriteria("name", productFilter.getName(), SearchOperation.MATCH));
        }
        if (productFilter.getDescription() != null) {
            dynamicUserSpec.add(new SearchCriteria("role", productFilter.getDescription(), SearchOperation.MATCH));
        }
        return dynamicUserSpec;
    }

    public static Specification<Product> getProductByVendor(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest) {
        Specification<Product> vendorEntitySpecification = new GenericSpecification<>();
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

    private static Specification<Product> getProductSpecificationByVendorId(Long vendorId) {
        Specification<Product> vendorEntitySpecification;
        vendorEntitySpecification = (root, query, criteriaBuilder) -> {
            Join<Product, Vendor> vendorJoin = root.join("vendor");
            Predicate equalPredicate = criteriaBuilder.equal(vendorJoin.get("vendorId"), vendorId);
            query.distinct(true);
            return equalPredicate;
        };
        return vendorEntitySpecification;
    }

    public static Specification<Product> getProductByCategory(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest) {
        Specification<Product> categoryEntitySpecification = new GenericSpecification<>();
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
    private static Specification<Product> getProductSpecificationByCategoryId(Long categoryId) {
        Specification<Product> categoryEntitySpecification;
        categoryEntitySpecification = (root, query, criteriaBuilder) -> {
            Join<Product, ProductCategory> vendorJoin = root.join("ProductCategory");
            Predicate equalPredicate = criteriaBuilder.equal(vendorJoin.get("categoryId"), categoryId);
            query.distinct(true);
            return equalPredicate;
        };
        return categoryEntitySpecification;
    }

}
