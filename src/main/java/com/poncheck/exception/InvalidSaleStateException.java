package com.poncheck.exception;

public class InvalidSaleStateException extends RuntimeException {
    private final String code;
    public InvalidSaleStateException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
