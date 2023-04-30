package com.miu.onlinemarketplace.exception;

import lombok.Getter;

@Getter
public class JasperTemplateNotFound extends RuntimeException {
    private final String message;

    public JasperTemplateNotFound(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
