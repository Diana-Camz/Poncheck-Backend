package com.poncheck.dto.response.user;

import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record UserSaleResponseDTO(
        Long id,
        String name,
        Role role
) {
    public UserSaleResponseDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }
}