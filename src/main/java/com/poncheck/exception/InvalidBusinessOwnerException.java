package com.poncheck.exception;

public class InvalidBusinessOwnerException extends RuntimeException {
    private final String code;
    public InvalidBusinessOwnerException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
