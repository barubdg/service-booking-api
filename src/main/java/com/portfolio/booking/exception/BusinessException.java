package com.portfolio.booking.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message)
    {
        super(message);
    }
}