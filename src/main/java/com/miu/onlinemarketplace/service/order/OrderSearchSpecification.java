package com.miu.onlinemarketplace.service.order;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.service.generic.GenericSpecification;
import com.miu.onlinemarketplace.service.generic.SearchCriteria;
import com.miu.onlinemarketplace.service.generic.SearchOperation;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;

public class OrderSearchSpecification {

    public static GenericSpecification<Order> processDynamicOrderFilter(GenericFilterRequestDTO<OrderDto> genericFilterRequest) {
        GenericSpecification<Order> dynamicOrderSpec = new GenericSpecification<Order>();
        OrderDto orderFilter = genericFilterRequest.getDataFilter();
        if (orderFilter.getOrderId() != null && orderFilter.getOrderId() > 0) {
            dynamicOrderSpec.add(new SearchCriteria("orderId", orderFilter.getOrderId(), SearchOperation.EQUAL));
        }
        if (orderFilter.getOrderCode() != null) {
            dynamicOrderSpec.add(new SearchCriteria("orderCode", orderFilter.getOrderCode(), SearchOperation.MATCH));
        }
        return dynamicOrderSpec;
    }
}

