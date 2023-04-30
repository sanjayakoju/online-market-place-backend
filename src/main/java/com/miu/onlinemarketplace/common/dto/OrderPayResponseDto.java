package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.OrderPayStatus;
import com.miu.onlinemarketplace.common.enums.PaymentStatus;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayResponseDto {

    private boolean success;
    private String message;
    private HttpStatus httpStatus;
    private String orderPayStatus;
    private Object body;
}
