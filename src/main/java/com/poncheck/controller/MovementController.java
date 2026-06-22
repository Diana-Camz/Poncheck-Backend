package com.poncheck.controller;

import com.poncheck.dto.request.inventory.CreateMovementRequestDTO;
import com.poncheck.dto.request.inventory.UpdateMovementRequestDTO;
import com.poncheck.dto.response.inventory.MovementItemResponseDTO;
import com.poncheck.dto.response.inventory.MovementResponseDTO;
import com.poncheck.enums.TypeInventoryMovement;
import com.poncheck.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory Movements", description = "Endpoints for managing inventory movements, including stock entries, exits, adjustments, and movement history tracking.")
@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService service;

    @Operation(summary = "Get movements by type", description = "Retrieves all inventory movements that match the specified movement type.")
    @GetMapping
    public ResponseEntity<List<MovementItemResponseDTO>> getMovementsByType(@RequestParam TypeInventoryMovement type){
        List<MovementItemResponseDTO> typeList = service.getMovementsByType(type);
        return ResponseEntity.ok(typeList);
    }

    @Operation(summary = "Get movements by product", description = "Retrieves the inventory movement history for a specific product.")
    @GetMapping("/products/{id}")
    public ResponseEntity<List<MovementItemResponseDTO>> getMovementsByProduct(@PathVariable Long id){
        List<MovementItemResponseDTO> typeList = service.getMovementsByProduct(id);
        return ResponseEntity.ok(typeList);
    }

    @Operation(summary = "Get movements by sale", description = "Retrieves all inventory movements generated from a specific sale.")
    @GetMapping("/sale/{id}")
    public ResponseEntity<List<MovementItemResponseDTO>> getMovementsBySale(@PathVariable Long id){
        List<MovementItemResponseDTO> typeList = service.getMovementsBySale(id);
        return ResponseEntity.ok(typeList);
    }

    @Operation(summary = "Get movement by ID", description = "Retrieves detailed information about a specific inventory movement.")
    @GetMapping("/{id}")
    public ResponseEntity<MovementResponseDTO> getMovementById(@PathVariable Long id){
        MovementResponseDTO movement = service.getMovementById(id);
        return ResponseEntity.ok(movement);
    }

    @Operation(summary = "Create inventory movement", description = "Creates one or more inventory movements and updates product stock accordingly.")
    @PostMapping
    public ResponseEntity <List<MovementItemResponseDTO>> createMovement(@RequestBody CreateMovementRequestDTO data) {
        List<MovementItemResponseDTO> movement = service.createMovement(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }

    @Operation(summary = "Update inventory movement", description = "Updates the description of an existing inventory movement.")
    @PatchMapping("/{id}")
    public ResponseEntity<MovementResponseDTO> updateMovement(@PathVariable Long id, @RequestBody UpdateMovementRequestDTO data){
        MovementResponseDTO movement = service.updateMovement(id, data);
        return ResponseEntity.ok(movement);
    }
}