package com.xpresspayments.xpress.exceptions.loginException;

public class InvalidLoginDetailsException extends RuntimeException{
    public InvalidLoginDetailsException(String invalidAccountDetails) {
        super(invalidAccountDetails);
    }

}
