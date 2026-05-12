package com.poncheck.dto.response.error;

public record ErrorResponseDTO(
        String message,
        int status
) {
}
