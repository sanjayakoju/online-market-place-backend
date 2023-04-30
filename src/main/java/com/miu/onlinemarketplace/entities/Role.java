package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.security.models.EnumRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long roleId;

    @Enumerated(value = EnumType.STRING)
    private EnumRole role;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
