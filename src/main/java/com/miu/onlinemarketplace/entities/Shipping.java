package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long shippingId;
    private String deliveryInstruction;
    @Enumerated(value = EnumType.STRING)
    private ShippingStatus shippingStatus;
    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addressId")
    private Address address;
}
