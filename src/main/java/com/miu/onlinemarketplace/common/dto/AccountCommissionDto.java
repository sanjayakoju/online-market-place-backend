package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCommissionDto {
    private Long accountCommissionId;
    private Double vendorCommission;
    private Double platformCommission;
    @CreationTimestamp
    private LocalDateTime createdDate;
    private OrderItem orderItem;
}
