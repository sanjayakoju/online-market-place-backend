package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.AddressType;
import com.miu.onlinemarketplace.common.enums.CardBrand;
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
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardInfoId;
    private String cardNumber;
    @Column(nullable = false)
    private String cardDigit;
    private Integer expYear;
    private Integer expMonth;
    private String cvc;
    @Enumerated(value = EnumType.STRING)
    private CardBrand cardBrand;
    @Enumerated(value = EnumType.STRING)
    private AddressType addressType;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;


}
