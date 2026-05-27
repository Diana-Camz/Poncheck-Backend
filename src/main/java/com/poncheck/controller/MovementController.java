package com.poncheck.controller;

import com.poncheck.dto.request.inventory.CreateMovementRequestDTO;
import com.poncheck.dto.request.inventory.UpdateMovementRequestDTO;
import com.poncheck.dto.response.inventory.MovementItemResponseDTO;
import com.poncheck.dto.response.inventory.MovementResponseDTO;
import com.poncheck.enums.TypeMovement;
import com.poncheck.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService service;

    @GetMapping
    public ResponseEntity<List<MovementItemResponseDTO>> getMovementsByType(TypeMovement type){
        List<MovementItemResponseDTO> typeList = service.getMovementsByType(type);
        return ResponseEntity.ok(typeList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<List<MovementItemResponseDTO>> getMovementsByProduct(@PathVariable Long id){
        List<MovementItemResponseDTO> typeList = service.getMovementsByProduct(id);
        return ResponseEntity.ok(typeList);
    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<List<MovementItemResponseDTO>> getMovementsBySale(@PathVariable Long id){
        List<MovementItemResponseDTO> typeList = service.getMovementsBySale(id);
        return ResponseEntity.ok(typeList);
    }

    @PostMapping
    public ResponseEntity <List<MovementItemResponseDTO>> createMovement(@RequestBody CreateMovementRequestDTO data) {
        List<MovementItemResponseDTO> movement = service.createMovement(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovementResponseDTO> updateMovement(@PathVariable Long id, @RequestBody UpdateMovementRequestDTO data){
        MovementResponseDTO movement = service.updateMovement(id, data);
        return ResponseEntity.ok(movement);
    }
}