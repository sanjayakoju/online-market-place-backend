package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long addressId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private AddressType addressType;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
