package com.xpresspayments.xpress.exceptions;

import com.xpresspayments.xpress.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(IllegalArgumentException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Validation Error")
                .message(ex.getMessage())
                .detail("Ensure all request details are valid")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(IOException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Service Unavailable")
                .message(ex.getMessage())
                .detail("HTTP request was not successful")
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

}
