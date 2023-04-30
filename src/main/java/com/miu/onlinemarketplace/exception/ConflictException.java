package com.miu.onlinemarketplace.exception;

public class ConflictException extends RuntimeException{

    private String message;

    public ConflictException(String message){
        super(message);
    }

    public ConflictException(String message, Throwable cause){
        super(message, cause);
    }
}
