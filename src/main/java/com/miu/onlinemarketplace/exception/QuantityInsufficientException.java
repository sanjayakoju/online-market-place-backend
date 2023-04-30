package com.miu.onlinemarketplace.exception;

import lombok.Getter;

@Getter
public class QuantityInsufficientException extends RuntimeException {

    private final String message;

    public QuantityInsufficientException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
