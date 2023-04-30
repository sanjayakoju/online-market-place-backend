package com.miu.onlinemarketplace.service.ordermanage;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderItemDto;
import com.miu.onlinemarketplace.common.dto.OrderResponseDto;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.exception.AppSecurityException;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import com.miu.onlinemarketplace.repository.OrderRepository;
import com.miu.onlinemarketplace.repository.VendorRepository;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import com.miu.onlinemarketplace.service.order.OrderSearchSpecification;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    public OrderManagementServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    public Page<OrderResponseDto> getAllOrderOfCurrentUser(Pageable pageable) {
        Long userId = AppSecurityUtils.getCurrentUserId().orElseThrow(() -> new DataNotFoundException("User ID Not Found")); //get current userId
        Page<Order> orderPage = orderRepository.findAllByUser_UserId(userId, pageable);
        List<OrderResponseDto> orderResponseDtoList = orderPage.getContent().stream()
                .map(order -> {
                    List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
                    return mapToOrderResponseDto(order, orderItemList);
                }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, orderPage.getTotalElements());
    }

    public Page<OrderResponseDto> getAllOrderByVendor(Pageable pageable) {
        //EnumRole enumRole = AppSecurityUtils.getCurrentUserRole().orElseThrow(() -> new AppSecurityException("Required Role, but not available"));
        Long userId = AppSecurityUtils.getCurrentUserId().orElseThrow(() -> new DataNotFoundException("User ID Not Found"));// if currentUser role is vendor, get id
        Vendor vendor = vendorRepository.findByUser_UserId(userId).orElseThrow(() -> new DataNotFoundException("Vendor doesn't have linked UserId"));
        Long vendorId = vendor.getVendorId();

        //        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Order> orderPage = orderRepository.findOrdersByVendorId(vendorId, pageable);
//        Long totalOrderOfVendor = orderRepository.countOrdersByVendorId(vendorId);
        List<OrderResponseDto> orderResponseDtoList = orderPage.getContent().stream()
                .map(order -> {
                    List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderIdAndVendorId(order.getOrderId(), vendorId);
                    return mapToOrderResponseDto(order, orderItemList);
                }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, orderPage.getTotalElements());
    }

    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<OrderResponseDto> orderResponseDtoList = orderPage.stream().map(order -> {
            List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
            return mapToOrderResponseDto(order, orderItemList);
        }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, orderPage.getTotalElements());
    }

    public OrderResponseDto mapToOrderResponseDto(Order order, List<OrderItem> orderItemList) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        List<OrderItemDto> orderItemDtoList = orderItemList.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                .toList();
        double total = 0.0;
        for (OrderItemDto o : orderItemDtoList) {
            total += (o.getQuantity() * o.getPrice()) + o.getTax() - o.getDiscount();
        }
        // returning OrderResponseDto
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDto(orderDto);
        orderResponseDto.setTotal(total);
        orderResponseDto.setRelatedOrderItems(orderItemDtoList);
        return orderResponseDto;
    }

    @Override
    public Page<OrderResponseDto> filterOrderData(GenericFilterRequestDTO<OrderDto> genericFilterRequest, Pageable pageable) {

        EnumRole currentUserRole = AppSecurityUtils.getCurrentUserRole()
                .orElseThrow(() -> new AppSecurityException("The table filter not available for guest users"));
        if (currentUserRole.equals(EnumRole.ROLE_USER)) {
            throw new AppSecurityException("You dont have the required privilege for this.");
        }
        // Vendor can only see their product, ALso, no specification available for now
        Page<Order> filteredOrderPage;
        if (currentUserRole.equals(EnumRole.ROLE_VENDOR)) {
            Long currentUserId = AppSecurityUtils.getCurrentUserId()
                    .orElseThrow(() -> new AppSecurityException("The table filter not available for guest users"));
            Vendor vendor = vendorRepository.findByUser_UserId(currentUserId)
                    .orElseThrow(() -> new DataNotFoundException("Linked vendor with given Id, not found"));
            Long orderId = Optional.ofNullable(genericFilterRequest)
                    .map(orderDtoGenericFilterRequestDTO -> orderDtoGenericFilterRequestDTO.getDataFilter())
                    .map(orderDto -> orderDto.getOrderId())
                    .orElse(0L);
            if (orderId > 0) {
                filteredOrderPage = orderRepository.findOrdersByVendorIdAndOrderId(vendor.getVendorId(), genericFilterRequest.getDataFilter().getOrderId(), pageable);
            } else {
                filteredOrderPage = orderRepository.findOrdersByVendorId(vendor.getVendorId(), pageable);
            }
        } else {
            Specification<Order> specification = Specification
                    .where(OrderSearchSpecification.processDynamicOrderFilter(genericFilterRequest));
            filteredOrderPage = orderRepository.findAll(specification, pageable);
        }
        List<OrderResponseDto> orderResponseDtoList = filteredOrderPage.stream().map(order -> {
            List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
            return mapToOrderResponseDto(order, orderItemList);
        }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, filteredOrderPage.getTotalElements());
    }

}
