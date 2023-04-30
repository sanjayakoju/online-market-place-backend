package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseDto {

    private String message;
    private boolean isSuccess;
    private HttpStatus status;
    private Object data;

}
