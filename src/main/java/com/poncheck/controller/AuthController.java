package com.poncheck.controller;

import com.poncheck.dto.request.auth.LoginRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;
import com.poncheck.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO data){
        AuthResponseDTO userLogged = authService.login(data);
        return ResponseEntity.ok(userLogged);
    }
}
