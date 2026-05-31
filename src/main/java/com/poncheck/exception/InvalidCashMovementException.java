package com.poncheck.exception;

public class InvalidCashMovementException extends RuntimeException {
    public InvalidCashMovementException(String message) {
        super(message);
    }
}
