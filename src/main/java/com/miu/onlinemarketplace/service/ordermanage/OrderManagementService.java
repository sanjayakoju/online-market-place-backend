package com.miu.onlinemarketplace.service.ordermanage;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderResponseDto;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderManagementService {
    Page<OrderResponseDto> getAllOrderOfCurrentUser(Pageable pageable);

    Page<OrderResponseDto> getAllOrderByVendor(Pageable pageable);

    Page<OrderResponseDto> getAllOrders(Pageable pageable);

    Page<OrderResponseDto> filterOrderData(GenericFilterRequestDTO<OrderDto> genericFilterRequest, Pageable pageable);


}
