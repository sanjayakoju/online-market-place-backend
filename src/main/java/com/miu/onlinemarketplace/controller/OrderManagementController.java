package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderResponseDto;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import com.miu.onlinemarketplace.service.ordermanage.OrderManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage-order")
public class OrderManagementController {

    private final OrderManagementService orderManagementService;


    public OrderManagementController(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    //get for user
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity<?> getOrderUser(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderResponseDto> orderResponseDtos = orderManagementService.getAllOrderOfCurrentUser(pageable);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }


    //get for vendor
    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/vendor")
    public ResponseEntity<?> getOrderVendor(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderResponseDto> orderResponseDtos = orderManagementService.getAllOrderByVendor(pageable);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }


    //get for Admin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> getOrderAdmin(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderResponseDto> orderResponseDtos = orderManagementService.getAllOrders(pageable);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VENDOR')")
    @PostMapping("/filter")
    public ResponseEntity<?> filterOrderData(@RequestBody GenericFilterRequestDTO<OrderDto> genericFilterRequest, Pageable pageable) {
        log.info("Order API: Filter order data");
        Page<OrderResponseDto> orderDtoPage = orderManagementService.filterOrderData(genericFilterRequest, pageable);
        return new ResponseEntity<>(orderDtoPage, HttpStatus.OK);
    }


}
