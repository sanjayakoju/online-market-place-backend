package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import com.miu.onlinemarketplace.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@PreAuthorize("isAuthenticated()")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDto orderDto) {
        if (orderDto != null)
            return new ResponseEntity<>(orderService.saveOrder(orderDto), HttpStatus.OK);
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getById(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    // TODO - move this code to public endpoint
    @GetMapping()
    public ResponseEntity<?> getOrderItemList(@RequestParam String productCode) {
        return new ResponseEntity<>(orderService.getAllOrderItemsByOrderCode(productCode), HttpStatus.OK);
    }

    // use this to ship order to Deliver by admin/vendor
    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId) {
        if (orderId != null)
            return new ResponseEntity<>(orderService.updateOrderStatus(orderId), HttpStatus.OK);
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

}
