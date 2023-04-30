package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.attest.Attest;
import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.OrderPayInfoDto;
import com.miu.onlinemarketplace.common.dto.OrderPayResponseDto;
import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;
import com.miu.onlinemarketplace.service.payment.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/order/pay")
@Validated
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/info")
    public ResponseEntity<OrderPayInfoDto> getOrderPayInfo(@Attest @Valid @RequestBody List<ShoppingCartDto> shoppingCartDtos) {
        return new ResponseEntity<>(paymentService.findOrderPayInfo(shoppingCartDtos), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderPayResponseDto> createOrderPay(@Attest @Valid @RequestBody OrderPayDto orderPayDto){
        System.out.println("======orderPayDto====================================");
        return new ResponseEntity<>(paymentService.createOrderPay(orderPayDto), HttpStatus.OK);
    }
}
