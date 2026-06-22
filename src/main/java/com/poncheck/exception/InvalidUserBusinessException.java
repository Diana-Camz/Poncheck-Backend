package com.poncheck.exception;

public class InvalidUserBusinessException extends RuntimeException {
    public InvalidUserBusinessException(String message) {
        super(message);
    }
}
