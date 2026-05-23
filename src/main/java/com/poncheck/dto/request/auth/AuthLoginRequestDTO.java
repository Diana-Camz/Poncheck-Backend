package com.poncheck.dto.request.auth;

public record AuthLoginRequestDTO(
        String username,
        String password
) {
}
