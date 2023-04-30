package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.OrderPayStatus;
import com.miu.onlinemarketplace.common.enums.PaymentStatus;
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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Boolean isGuestUser;
    private String clientIp;
    private String cardId;
    @Enumerated(value = EnumType.STRING)
    private OrderPayStatus orderPayStatus;
    private String transactionId;

    private Long userId;
    private String fullName;
    private String email;
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="addressId")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardInfoId")
    private CardInfo cardInfo;

    @CreationTimestamp
    private LocalDateTime createdDate;

}
