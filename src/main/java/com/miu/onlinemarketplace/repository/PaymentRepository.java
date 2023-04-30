package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
