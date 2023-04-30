package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.service.domain.users.VendorService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<?> verifyVendor(@RequestBody VendorDto vendorDto) {
        if (vendorDto != null)
            return new ResponseEntity<>(vendorService.verifyVendor(vendorDto), HttpStatus.OK);
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping()
    public ResponseEntity<?> getAllVendors(@PageableDefault(page = 0, size = 10, sort = "vendorId",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VendorDto> vendorPageable = vendorService.getAllVendors(pageable);
        return new ResponseEntity<>(vendorPageable, HttpStatus.OK);
    }

    @PostAuthorize("hasAnyRole('ROLE_ADMIN') or returnObject.userDto.id == authentication.principal.userId")
    @GetMapping("/{vendorId}")
    public ResponseEntity<?> getByVendorId(@PathVariable Long id) {
        VendorDto returnedVendorDto = vendorService.getVendorById(id);
        // If @PostAuthorize doesn't work, replace with the code below
//        Long currentUserId = Optional.ofNullable(returnedVendorDto)
//                .map(vendorDto -> vendorDto.getUserDto())
//                .map(userDto -> userDto.getId())
//                .map(userId -> userId.equals(AppSecurityUtils.getCurrentUserId()) ? userId : null)
//                .orElseThrow(() -> new AccessDeniedException("You are not authorized to access this resource"));
        return new ResponseEntity<>(returnedVendorDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<?> filterVendorData(@RequestBody GenericFilterRequestDTO<VendorDto> genericFilterRequest, Pageable pageable) {
        log.info("Vendor API: Filter vendor data");
        Page<VendorDto> vendorPageable = vendorService.filterVendorData(genericFilterRequest, pageable);
        return new ResponseEntity<>(vendorPageable, HttpStatus.OK);
    }
}
