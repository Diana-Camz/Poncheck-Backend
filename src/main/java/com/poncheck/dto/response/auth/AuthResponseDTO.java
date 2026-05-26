package com.poncheck.dto.response.auth;

import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record AuthResponseDTO(
        Long id,
        String name,
        String username,
        Role role,
        String jwtToken,
        String refreshToken

) {
    public AuthResponseDTO(User user, String jwtToken, String refreshToken){
        this(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                jwtToken,
                refreshToken

        );
    }
}
