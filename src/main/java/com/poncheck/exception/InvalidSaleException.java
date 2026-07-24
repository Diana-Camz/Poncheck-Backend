package com.poncheck.exception;

public class InvalidSaleException extends RuntimeException {
    private final String code;
    public InvalidSaleException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
