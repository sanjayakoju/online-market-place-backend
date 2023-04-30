package com.miu.onlinemarketplace.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    private final String message;

    public DataNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
