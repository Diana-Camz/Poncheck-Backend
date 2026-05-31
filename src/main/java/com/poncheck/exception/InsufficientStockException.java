package com.poncheck.exception;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {
    private final Long resourceId;
    public InsufficientStockException(
            String message,
            Long resourceId
    ) {
        super(message);
        this.resourceId = resourceId;
    }
}
