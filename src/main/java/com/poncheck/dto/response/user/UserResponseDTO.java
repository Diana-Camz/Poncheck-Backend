package com.poncheck.dto.response.user;

import com.poncheck.dto.response.business.BusinessResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String username,
        Role role,
        Boolean active,
        BusinessResponseDTO business
) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getActive(),
                (user.getBusiness() != null ) ? new BusinessResponseDTO(user.getBusiness()) : null
        );
    }

}
