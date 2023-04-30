package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.common.dto.AddressDto;
import com.miu.onlinemarketplace.entities.Address;
import com.miu.onlinemarketplace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUser(User user);

    List<Address> findByUserUserId(Long userId);
}
