package com.poncheck.service;

import com.poncheck.dto.request.auth.AuthLoginRequestDTO;
import com.poncheck.dto.request.auth.AuthRegisterRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;
import com.poncheck.dto.response.token.TokenResponseDTO;
import com.poncheck.dto.response.user.UserResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthLoginRequestDTO data);
    AuthResponseDTO register(AuthRegisterRequestDTO user);
    TokenResponseDTO refreshToken(String authHeader);
    void logout(String authHeader);
}
