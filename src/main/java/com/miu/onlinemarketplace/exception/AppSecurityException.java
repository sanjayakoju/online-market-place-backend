package com.miu.onlinemarketplace.exception;

import lombok.Getter;

@Getter
public class AppSecurityException extends RuntimeException {

    private final String message;

    public AppSecurityException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
