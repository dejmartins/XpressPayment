package com.xpresspayments.xpress.exceptions.loginException;

import com.xpresspayments.xpress.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(InvalidLoginDetailsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(InvalidLoginDetailsException ex){
        ErrorResponse response = ErrorResponse.builder()
                .error("Log 0001")
                .message(ex.getMessage())
                .detail("Ensure that the email and password are correct.")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
