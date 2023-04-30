package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {

    Optional<Vendor> findByUser_UserId(Long userId);
}
