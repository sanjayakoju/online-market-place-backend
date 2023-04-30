package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Boolean isVerified;
    @Column(nullable = false)
    private Boolean isDeleted;
    @Column(nullable = false)
    private Double price;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private ProductCategory productCategory;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<FileEntity> images;

}
