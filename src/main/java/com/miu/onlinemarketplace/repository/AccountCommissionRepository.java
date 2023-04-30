package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.AccountCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCommissionRepository extends JpaRepository<AccountCommission, Long> {
}
