package com.miu.onlinemarketplace.service.order;

import com.miu.onlinemarketplace.common.dto.CheckingOrderDto;
import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto getOrderById(Long orderId);

    Boolean deleteOrderById(Long orderId);

    List<OrderDto> getAllOrders();

    List<OrderDto> getAllOrderByUserId(Long userId);

    CheckingOrderDto getAllOrderItemsByOrderCode(String orderCode);

    boolean updateOrderStatus(Long orderId);
}
