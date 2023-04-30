package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findAllByName(Pageable pageable, String name);
    Page<Product> findAllByProductCategory(Pageable pageable, Long categoryId);

    @Query(value = "SELECT p from Product as p where p.isVerified = :isVerified and p.isDeleted= :isDeleted and p.productCategory.categoryId = :categoryId")
    Page<Product> findByIsDeletedAndIsVerified(Pageable pageable, Boolean isVerified, Boolean isDeleted, Long categoryId);

    Page<Product> findAllByVendor_VendorIdAndIsDeletedIsFalse(Pageable pageable, Long vendorId);
}
