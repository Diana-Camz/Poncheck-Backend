package com.poncheck.dto.request.auth;

import com.poncheck.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record AuthRegisterRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String username,
        @NotBlank
        String password,
        Role role
) {
}
