package com.poncheck.controller;

import com.poncheck.dto.request.auth.AuthLoginRequestDTO;
import com.poncheck.dto.request.auth.AuthRegisterRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;
import com.poncheck.dto.response.token.TokenResponseDTO;
import com.poncheck.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints for user authentication, registration, JWT token management, and session control.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Authenticates a user and returns an access token and refresh token.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthLoginRequestDTO data){
        AuthResponseDTO userLogged = authService.login(data);
        return ResponseEntity.ok(userLogged);
    }

    @Operation(summary = "Register user", description = "Creates a new user account according to the permissions of the authenticated user.")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRegisterRequestDTO userData){
        AuthResponseDTO user = authService.register(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Hidden
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        TokenResponseDTO refreshToken = authService.refreshToken(authHeader);
        return ResponseEntity.ok(refreshToken);
    }

    @Hidden
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
            authService.logout(authHeader);
            return ResponseEntity.noContent().build();

    }
}
