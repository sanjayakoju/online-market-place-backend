package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    List<CardInfo> findByUserUserId(Long userId);

    Optional<CardInfo> findByCardDigit(String lastFourDigits);
}
