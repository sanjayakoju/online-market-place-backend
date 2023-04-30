package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.AddressType;
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
