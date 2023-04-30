package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p JOIN p.ProductCategory c " +
//            "WHERE (:name IS NULL OR p.name = :name) " +
//            "OR (:categoryName IS NULL OR c.name = :categoryName) " +
//            "OR (:price = 0 OR p.price = :price)")
//    List<Product> search(String q, String c, String p);
}
