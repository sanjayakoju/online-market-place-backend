package com.miu.onlinemarketplace.entities;


import com.miu.onlinemarketplace.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    private String orderCode;
    @CreationTimestamp
    private LocalDateTime orderDate;
    @OneToOne
    @JoinColumn(name = "shippingId")
    private Shipping shipping;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Payment> payments;



}
