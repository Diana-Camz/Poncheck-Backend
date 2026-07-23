package com.poncheck.exception;

public class InvalidCashMovementException extends RuntimeException {
    private final String code;
    public InvalidCashMovementException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
