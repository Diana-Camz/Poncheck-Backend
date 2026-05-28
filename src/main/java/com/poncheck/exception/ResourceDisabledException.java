package com.poncheck.exception;

import lombok.Getter;

@Getter
public class ResourceDisabledException extends RuntimeException {
    private final Long resourceId;
    public ResourceDisabledException(
            String message,
            Long resourceId
    ) {
            super(message);
            this.resourceId = resourceId;
        }
}
