package com.miu.onlinemarketplace.service.domain.users;

import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.service.domain.users.dtos.VendorRegistrationRequest;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {

    Page<VendorDto> getAllVendors(Pageable pageable);

    VendorDto getVendorById(Long vendorId);

    VendorDto registerVendor(VendorRegistrationRequest vendorRegistrationRequest);

    Page<VendorDto> filterVendorData(GenericFilterRequestDTO<VendorDto> genericFilterRequest, Pageable pageable);

    VendorDto verifyVendor(VendorDto vendorDto);

}
