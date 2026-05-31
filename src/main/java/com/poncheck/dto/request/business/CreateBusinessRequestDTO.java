package com.poncheck.dto.request.business;

import com.poncheck.entity.User;
import jakarta.validation.constraints.NotNull;

public record CreateBusinessRequestDTO(
        @NotNull
        String name,
        String businessCode,
        String phone,
        String email,
        String address,
        String description,
        String logoUrl,
        Long ownerId
) {
}
