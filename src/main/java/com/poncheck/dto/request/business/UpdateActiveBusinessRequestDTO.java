package com.poncheck.dto.request.business;

import jakarta.validation.constraints.NotNull;

public record UpdateActiveBusinessRequestDTO(
        @NotNull
        Boolean active
) {
}
