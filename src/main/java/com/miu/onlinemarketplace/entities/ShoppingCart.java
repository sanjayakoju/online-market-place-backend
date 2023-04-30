package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long cartId;
    private Integer quantity;
    @CreationTimestamp
    private LocalDateTime createdDate;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_id")
    @OneToOne
    private Product product;
}
