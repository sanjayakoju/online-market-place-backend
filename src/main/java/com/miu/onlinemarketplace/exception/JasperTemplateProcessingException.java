package com.miu.onlinemarketplace.exception;

import lombok.Getter;

@Getter
public class JasperTemplateProcessingException extends RuntimeException {
    private final String message;

    public JasperTemplateProcessingException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
