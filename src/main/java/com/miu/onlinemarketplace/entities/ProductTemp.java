package com.miu.onlinemarketplace.entities;

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
public class ProductTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer quantity;
    private Boolean isVerified;
    private Boolean isDeleted;
    private Double price;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private ProductCategory productCategory;
}
