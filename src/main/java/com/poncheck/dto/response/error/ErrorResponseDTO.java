package com.poncheck.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorResponseDTO(
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String resource,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long resourceId,
        int status
) {
    public ErrorResponseDTO(String code, String message, int status){
        this(code, message, null, null, status);
    }

    public ErrorResponseDTO(String code, String message, Long resourceId, int status) {
        this(
                code, message, null,resourceId, status
        );
    }
}
