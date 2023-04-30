package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.ProductTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTempRepository extends JpaRepository<ProductTemp, Long>, JpaSpecificationExecutor<ProductTemp> {

    Page<ProductTemp> findAllByVendor_VendorIdAndIsDeletedIsFalse(Pageable pageable, Long vendorId);
}
