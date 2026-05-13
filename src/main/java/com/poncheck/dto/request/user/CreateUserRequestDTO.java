package com.poncheck.dto.request.user;

import com.poncheck.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDTO(
        @NotBlank
        String name,
        @NotBlank

        String username,
        @NotBlank
        String password,
        @NotNull
        Role role
) {
}
