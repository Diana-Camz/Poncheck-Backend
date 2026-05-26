package com.poncheck.controller;

import com.poncheck.dto.request.auth.AuthLoginRequestDTO;
import com.poncheck.dto.request.auth.AuthRegisterRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;
import com.poncheck.dto.response.token.TokenResponseDTO;
import com.poncheck.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthLoginRequestDTO data){
        AuthResponseDTO userLogged = authService.login(data);
        return ResponseEntity.ok(userLogged);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRegisterRequestDTO userData){
        AuthResponseDTO user = authService.register(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        TokenResponseDTO refreshToken = authService.refreshToken(authHeader);
        return ResponseEntity.ok(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
            authService.logout(authHeader);
            return ResponseEntity.noContent().build();

    }
}
