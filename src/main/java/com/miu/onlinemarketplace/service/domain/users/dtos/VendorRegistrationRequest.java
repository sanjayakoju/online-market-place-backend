package com.miu.onlinemarketplace.service.domain.users.dtos;

import com.miu.onlinemarketplace.common.dto.CardInfoRequest;
import lombok.Data;

@Data
public class VendorRegistrationRequest {

    private VendorType vendorType;
    private String email;
    private String password;
    private String companyName;
    private String description;
    private CardInfoRequest cardInfo;
}
