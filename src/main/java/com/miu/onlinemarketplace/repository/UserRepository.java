package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.common.enums.UserStatus;
import com.miu.onlinemarketplace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

//    @Query("SELECT u FROM User u WHERE u.email = :username AND u.userStatus = :userStatus")
//    Optional<User> findByEmailAndUserStatus(@Param("username") String username, @Param("userStatus") UserStatus userStatus);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email " +
            "and u.emailVerificationCodeExpiresAt >= current_timestamp and u.emailVerificationCode = :emailVerificationCode")
    Optional<User> verifySignUpEmailByVerificationCode(@Param("email") String email, @Param("emailVerificationCode") String emailVerificationCode);

    @Query("SELECT u FROM User u WHERE u.email = :email " +
            "and u.verificationCodeExpiresAt >= current_timestamp and u.verificationCode = :verificationCode")
    Optional<User> verifyPasswordResetVerificationRequest(@Param("email") String email, @Param("verificationCode") String verificationCode);

}
