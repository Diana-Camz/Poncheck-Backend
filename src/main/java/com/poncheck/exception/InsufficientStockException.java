package com.poncheck.exception;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {
    private final Long resourceId;
    private final String code;
    public InsufficientStockException(
            String code,
            String message,
            Long resourceId
    ) {
        super(message);
        this.code = code;
        this.resourceId = resourceId;
    }
}
