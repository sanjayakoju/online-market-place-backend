package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.service.order.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderItem")
// TODO - Please check the use of this class. If required implement and also integrate with UI, else remove.
public class OrderItemController {

    private OrderService orderService;

    public OrderItemController(OrderService orderService) {
        this.orderService = orderService;
    }
}
