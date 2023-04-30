package com.miu.onlinemarketplace.service.orderitems;

import com.miu.onlinemarketplace.common.dto.OrderItemDto;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    private ModelMapper modelMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderItemDto saveOrder(OrderItemDto orderItemDto) {
        return null;
    }

    @Override
    public Boolean delete(Long orderItemId) {
        return null;
    }

    @Override
    public List<OrderItemDto> getAllOrderItemListByOrderId(Long orderId) {
        return null;
    }
}
