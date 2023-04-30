package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String fullName;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;
    @CreationTimestamp
    private LocalDateTime createdDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    private String emailVerificationCode;
    private LocalDateTime emailVerificationCodeExpiresAt;

    private String verificationCode;
    private LocalDateTime verificationCodeExpiresAt;


}
