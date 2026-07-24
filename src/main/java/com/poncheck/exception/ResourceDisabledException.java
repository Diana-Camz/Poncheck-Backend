package com.poncheck.exception;

import lombok.Getter;

@Getter
public class ResourceDisabledException extends RuntimeException {
    private final Long resourceId;
    private final String code;
    public ResourceDisabledException(
            String code,
            String message,
            Long resourceId
    ) {
            super(message);
            this.resourceId = resourceId;
            this.code = code;
        }
}
