package com.poncheck.dto.request.user;

import jakarta.validation.constraints.NotNull;

public record UpdateActiveUserRequestDTO(
        @NotNull Boolean active
) {
}
