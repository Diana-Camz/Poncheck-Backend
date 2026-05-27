package com.poncheck.exception;


import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final Long resourceId;
    public ResourceNotFoundException(
            String message,
            String resource,
            Long resourceId
    ){
        super(message);
        this.resource = resource;
        this.resourceId = resourceId;
    }
}
