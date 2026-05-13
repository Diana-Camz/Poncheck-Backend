package com.poncheck.controller;

import com.poncheck.dto.request.user.CreateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    //Creates new User
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO userData){
        UserResponseDTO user = service.createUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
