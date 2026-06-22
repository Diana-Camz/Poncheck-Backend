package com.poncheck.controller;

import com.poncheck.dto.request.cash.CashMovementCreateRequestDTO;
import com.poncheck.dto.request.cash.UpdateCashMovementRequestDTO;
import com.poncheck.dto.response.cash.CashMovementResponseDTO;
import com.poncheck.enums.TypeCashMovement;
import com.poncheck.service.CashMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Cash Movements", description = "Endpoints for managing cash movements, including sales, deposits, withdrawals, payments, and cash flow tracking.")
@RestController
@RequestMapping("/register/movements")
@RequiredArgsConstructor
public class CashMovementController {
    private final CashMovementService service;

    @Operation(summary = "Get cash movements by type", description = "Retrieves all cash movements that match the specified cash movement type.")
    @GetMapping
    public ResponseEntity<List<CashMovementResponseDTO>> getMovementsByType(TypeCashMovement type){
        List<CashMovementResponseDTO> typeList = service.getMovementsByType(type);
        return ResponseEntity.ok(typeList);
    }

    @Operation(summary = "Get cash movements by sale", description = "Retrieves all cash movements associated with a specific sale.")
    @GetMapping("/sale/{id}")
    public ResponseEntity<List<CashMovementResponseDTO>> getMovementsBySale(@PathVariable Long id){
        List<CashMovementResponseDTO> typeList = service.getMovementsBySale(id);
        return ResponseEntity.ok(typeList);
    }

    @Operation(summary = "Get cash movement by ID", description = "Retrieves detailed information about a specific cash movement.")
    @GetMapping("/{id}")
    public ResponseEntity<CashMovementResponseDTO> getMovementById(@PathVariable Long id){
        CashMovementResponseDTO movement = service.getMovementById(id);
        return ResponseEntity.ok(movement);
    }

    @Operation(summary = "Get cash movements by date range", description = "Retrieves all cash movements recorded within the specified date range.")
    @GetMapping("/date-range")
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByDateRange(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        List<CashMovementResponseDTO> movementList = service.getCashMovementsByDateRange(start, end);
        return ResponseEntity.ok(movementList);
    }

    @Operation(summary = "Get sales cash movements by date range", description = "Retrieves cash movements generated from sales within the specified date range.")
    @GetMapping("/sales")
    public ResponseEntity<List<CashMovementResponseDTO>> getMovementsBySalesByDateRange(LocalDateTime start, LocalDateTime end){
        List<CashMovementResponseDTO> movementList = service.getCashMovementsByDateRange(start, end);
        return ResponseEntity.ok(movementList);
    }

    @Operation(summary = "Get cash movements by user", description = "Retrieves all cash movements performed by a specific user.")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByUser(@PathVariable Long id){
        List<CashMovementResponseDTO> registerList = service.getCashMovementsByUser(id);
        return ResponseEntity.ok(registerList);
    }

    @Operation(summary = "Create cash movement", description = "Creates a new cash movement such as a deposit, withdrawal, payment, or sale transaction.")
    @PostMapping
    public ResponseEntity<CashMovementResponseDTO> createMovement(@RequestBody CashMovementCreateRequestDTO data) {
        CashMovementResponseDTO movement = service.createMovement(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }


    @Operation(summary = "Update cash movement", description = "Updates the information of an existing cash movement record.")
    @PatchMapping("/{id}")
    public ResponseEntity<CashMovementResponseDTO> updateMovement(@PathVariable Long id, @RequestBody UpdateCashMovementRequestDTO data){
        CashMovementResponseDTO movement = service.updateMovement(id, data);
        return ResponseEntity.ok(movement);
    }
}
