package com.poncheck.exception;


import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String code;
    private final String resource;
    private final Long resourceId;
    public ResourceNotFoundException(
            String code,
            String message,
            String resource,
            Long resourceId
    ){
        super(message);
        this.code = code;
        this.resource = resource;
        this.resourceId = resourceId;
    }
}
