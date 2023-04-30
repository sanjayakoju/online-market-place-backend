package com.miu.onlinemarketplace.service.auth.dtos;

import com.miu.onlinemarketplace.common.dto.VendorDto;
import lombok.Data;

@Data
public class RegisterVendorRequestDTO {

    private RegisterUserRequestDTO registerUser;

    private VendorDto vendorDTO;

    // other details

}