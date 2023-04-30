package com.miu.onlinemarketplace.service.payment;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.OrderPayInfoDto;
import com.miu.onlinemarketplace.common.dto.OrderPayResponseDto;
import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;

import java.util.List;

public interface PaymentService {

    OrderPayInfoDto findOrderPayInfo(List<ShoppingCartDto> shoppingCartDtos);

    OrderPayResponseDto createOrderPay(OrderPayDto orderPayDto);
}
