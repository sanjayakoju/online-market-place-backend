package com.miu.onlinemarketplace.service.orderitems;

import com.miu.onlinemarketplace.common.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {

    OrderItemDto saveOrder(OrderItemDto orderItemDto);

    Boolean delete(Long orderItemId);

    List<OrderItemDto> getAllOrderItemListByOrderId(Long orderId);
}
