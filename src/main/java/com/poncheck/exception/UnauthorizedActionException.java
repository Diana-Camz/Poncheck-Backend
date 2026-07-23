package com.poncheck.exception;

public class UnauthorizedActionException extends RuntimeException {
    private final String code;
    public UnauthorizedActionException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
