package com.miu.onlinemarketplace.config;


import com.miu.onlinemarketplace.exception.AppSecurityException;
import com.miu.onlinemarketplace.exception.ConflictException;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.exception.ErrorDetails;
import com.miu.onlinemarketplace.exception.QuantityInsufficientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), 404, "Invalid User Input"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleValidationError(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(createErrorDetails("Validation Error", 400, ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorDetails createErrorDetails(String message, Integer status, String type) {
        return ErrorDetails
                .builder()
                .message(message)
                .status(status)
                .type(type)
                .build();
    }


    @ExceptionHandler(QuantityInsufficientException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleInsufficientQuantityException(QuantityInsufficientException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), 404, "Invalid User Input"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppSecurityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleAppSecurityException(AppSecurityException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), 404, "Invalid User Input"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorDetails> handleConflictException(
            ConflictException ex){
        return new ResponseEntity<>(
                createErrorDetails(ex.getMessage(), 404, "Invalid inputs"), HttpStatus.CONFLICT);
    }
}
