package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.ProductCategory;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
