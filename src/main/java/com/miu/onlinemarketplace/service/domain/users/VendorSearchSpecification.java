package com.miu.onlinemarketplace.service.domain.users;

import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.service.generic.GenericSpecification;
import com.miu.onlinemarketplace.service.generic.SearchCriteria;
import com.miu.onlinemarketplace.service.generic.SearchOperation;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;

public class VendorSearchSpecification {

    public static GenericSpecification<Vendor> processDynamicVendorFilter(GenericFilterRequestDTO<VendorDto> genericFilterRequest) {
        GenericSpecification<Vendor> dynamicVendorSpec = new GenericSpecification<Vendor>();
        VendorDto vendorFilter = genericFilterRequest.getDataFilter();
        if (vendorFilter.getVendorId() != null && vendorFilter.getVendorId() > 0) {
            dynamicVendorSpec.add(new SearchCriteria("vendorId", vendorFilter.getVendorId(), SearchOperation.EQUAL));
        }
        if (vendorFilter.getVendorName() != null) {
            dynamicVendorSpec.add(new SearchCriteria("vendorName", vendorFilter.getVendorName(), SearchOperation.MATCH));
        }
        if (vendorFilter.getDescription() != null) {
            dynamicVendorSpec.add(new SearchCriteria("role", vendorFilter.getDescription(), SearchOperation.MATCH));
        }
        return dynamicVendorSpec;
    }

}
