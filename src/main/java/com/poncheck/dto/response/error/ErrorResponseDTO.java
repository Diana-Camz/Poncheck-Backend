package com.poncheck.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorResponseDTO(
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String resource,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long resourceId,
        int status
) {
    public ErrorResponseDTO(String message, int status){
        this(message, null, null, status);
    }

    public ErrorResponseDTO(String message, Long resourceId, int status) {
        this(
                message, null,resourceId, status
        );
    }
}
