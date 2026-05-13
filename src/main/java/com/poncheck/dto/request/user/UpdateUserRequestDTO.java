package com.poncheck.dto.request.user;

import com.poncheck.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDTO(
        @Size(max = 100)
        String name,
        String username,
        Role role
) {
}
