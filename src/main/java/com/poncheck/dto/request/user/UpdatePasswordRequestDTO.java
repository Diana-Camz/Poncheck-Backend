package com.poncheck.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequestDTO(
        @NotBlank String currentPassword,
        @NotBlank String newPassword
) {}