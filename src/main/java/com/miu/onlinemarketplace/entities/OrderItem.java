package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.OrderItemStatus;
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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Double price;
    private Double tax;
    private Integer quantity;
    private Double discount;
    private Boolean isCommissioned;

    @Enumerated(value=EnumType.STRING)
    private OrderItemStatus orderItemStatus; // Set default OrderItemStatus.REQUESTED;
    @CreationTimestamp
    private LocalDateTime createdDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

}
