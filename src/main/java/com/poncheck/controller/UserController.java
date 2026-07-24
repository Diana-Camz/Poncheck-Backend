package com.poncheck.controller;

import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Endpoints for managing business users, including owners and sellers, with role-based access control.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @Operation(summary = "Get all users", description = "Retrieves all users available in the system.")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        List<UserResponseDTO> users = service.getUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get active users", description = "Retrieves all users with active status.")
    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDTO>> getActiveUsers(){
        List<UserResponseDTO> users = service.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get inactive users", description = "Retrieves all users with inactive status.")
    @GetMapping("/inactive")
    public ResponseEntity<List<UserResponseDTO>> getInactiveUsers(){
        List<UserResponseDTO> users = service.getInactiveUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by its identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get current user", description = "Retrieves current user by its token.")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(){
        UserResponseDTO user = service.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user", description = "Updates user information by its identifier.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO userData){
        UserResponseDTO user = service.updateUser(id, userData);
        return ResponseEntity.ok(user);
    }


    @Operation(summary = "Update user active status", description = "Activates or deactivates a user (logical deletion).")
    @PatchMapping("/{id}/active")
    public ResponseEntity<UserResponseDTO> updateActive(@PathVariable Long id, @RequestBody UpdateActiveUserRequestDTO status){
        UserResponseDTO user = service.updateActive(id, status);
        return ResponseEntity.ok(user);
    }

    //@Operation(summary = "Delete user", description = "Permanently deletes a user from the system.")
    @Hidden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
