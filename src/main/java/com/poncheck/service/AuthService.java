package com.poncheck.service;

import com.poncheck.dto.request.auth.LoginRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO data);
}
