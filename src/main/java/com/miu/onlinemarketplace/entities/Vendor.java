package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long vendorId;
    private String vendorName;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "logoFileId")
    private FileEntity logo;
    private Boolean isVerified;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToMany
    List<Payment> payments;
}
