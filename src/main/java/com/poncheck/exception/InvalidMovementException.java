package com.poncheck.exception;

public class InvalidMovementException extends RuntimeException {
    private final String code;
    public InvalidMovementException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
