package com.poncheck.dto.request.business;

import com.poncheck.entity.User;
import jakarta.validation.constraints.NotNull;

public record UpdateOwnerBusinessRequestDTO(
        @NotNull
        Long ownerId
) {
}
