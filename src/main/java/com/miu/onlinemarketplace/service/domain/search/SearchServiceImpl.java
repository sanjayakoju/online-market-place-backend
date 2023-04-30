package com.miu.onlinemarketplace.service.domain.search;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private EntityManager entityManager;

    private ModelMapper modelMapper;

    public SearchServiceImpl(EntityManager entityManager, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ProductResponseDto> advanceSearch(String name, String categoryName, Double minPrice, Double maxPrice, String sortedPrice, Pageable pageable) {
        double minPriceValue = minPrice != null ? minPrice : 0.0;
        double maxPriceValue = maxPrice != null ? maxPrice : 0.0;

        String queryString = "SELECT p FROM Product p JOIN p.productCategory c";
        boolean whereClauseAdded = false;
        if (name != null && !name.isEmpty()) {
            queryString += " WHERE p.name LIKE CONCAT('%', :name, '%')";
            whereClauseAdded = true;
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            if (whereClauseAdded) {
                queryString += " AND c.category LIKE CONCAT('%', :categoryName, '%')";
            } else {
                queryString += " WHERE c.category LIKE CONCAT('%', :categoryName, '%')";
                whereClauseAdded = true;
            }
        }
        if (minPriceValue > 0 && maxPriceValue > 0) {
            if (whereClauseAdded) {
                queryString += " AND p.price BETWEEN :minPriceValue AND :maxPriceValue";
            } else {
                queryString += " WHERE p.price BETWEEN :minPriceValue AND :maxPriceValue";
            }
        } else if (minPriceValue > 0) {
            if (whereClauseAdded) {
                queryString += " AND p.price >= :minPriceValue";
            } else {
                queryString += " WHERE p.price >= :minPriceValue";
            }
        } else if (maxPriceValue > 0) {
            if (whereClauseAdded) {
                queryString += " AND p.price <= :maxPriceValue";
            } else {
                queryString += " WHERE p.price <= :maxPriceValue";
            }
        }

        if (whereClauseAdded) {
            queryString += " AND p.isDeleted=:isDeleted AND p.isVerified=:isVerified";
        } else {
            queryString += " WHERE p.isDeleted=:isDeleted AND p.isVerified=:isVerified";
        }

        if (sortedPrice != null && !sortedPrice.isEmpty()) {
            if (sortedPrice.equals("ASC")) {
                queryString += " ORDER BY p.price ASC";
            } else {
                queryString += " ORDER BY p.price DESC";
            }
        }


        TypedQuery<Product> query = entityManager.createQuery(queryString, Product.class);
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", name);
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            query.setParameter("categoryName", categoryName);
        }
        if (minPriceValue > 0 && maxPriceValue > 0) {
            query.setParameter("minPriceValue", minPriceValue);
            query.setParameter("maxPriceValue", maxPriceValue);
        } else if (minPriceValue > 0) {
            query.setParameter("minPriceValue", minPriceValue);
        } else if (maxPriceValue > 0) {
            query.setParameter("maxPriceValue", maxPriceValue);
        }
        query.setParameter("isVerified", true);
        query.setParameter("isDeleted", false);
//        if (sortedPrice != null && !sortedPrice.isEmpty()) {
//            query.setParameter("sortedPrice", sortedPrice);
//        }
        int totalCount = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<ProductResponseDto> resultList = query.getResultList().stream()
                .map(product -> {
                    ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
                    productResponseDto.setVendor(modelMapper.map(product.getVendor(), VendorDto.class));
                    productResponseDto.setProductCategory(modelMapper.map(product.getProductCategory(), ProductCategoryDto.class));
                    return productResponseDto;
                })
                .toList();
        return new PageImpl<>(resultList, pageable, totalCount);
    }
}
