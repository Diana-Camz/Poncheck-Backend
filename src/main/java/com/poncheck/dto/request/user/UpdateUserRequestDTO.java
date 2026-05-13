package com.poncheck.dto.request.user;

import com.poncheck.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String username,
        @NotBlank
        Role role
) {
}
