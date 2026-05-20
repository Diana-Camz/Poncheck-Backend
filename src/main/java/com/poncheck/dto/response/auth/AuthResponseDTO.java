package com.poncheck.dto.response.auth;

import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record AuthResponseDTO(
        String name,
        String username,
        Role role
) {
    public AuthResponseDTO(User user){
        this(
                user.getName(),
                user.getUsername(),
                user.getRole()

        );
    }
}
