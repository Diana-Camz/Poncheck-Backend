package com.poncheck.controller;

import com.poncheck.dto.request.cash.CashMovementCreateRequestDTO;
import com.poncheck.dto.request.cash.UpdateCashMovementRequestDTO;
import com.poncheck.dto.response.cash.CashMovementResponseDTO;
import com.poncheck.enums.TypeCashMovement;
import com.poncheck.service.CashMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/register/movements")
@RequiredArgsConstructor
public class CashMovementController {
    private final CashMovementService service;

    @GetMapping
    public ResponseEntity<List<CashMovementResponseDTO>> getMovementsByType(TypeCashMovement type){
        List<CashMovementResponseDTO> typeList = service.getMovementsByType(type);
        return ResponseEntity.ok(typeList);
    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<List<CashMovementResponseDTO>> getMovementsBySale(@PathVariable Long id){
        List<CashMovementResponseDTO> typeList = service.getMovementsBySale(id);
        return ResponseEntity.ok(typeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashMovementResponseDTO> getMovementById(@PathVariable Long id){
        CashMovementResponseDTO movement = service.getMovementById(id);
        return ResponseEntity.ok(movement);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByDateRange(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        List<CashMovementResponseDTO> movementList = service.getCashMovementsByDateRange(start, end);
        return ResponseEntity.ok(movementList);
    }

    @GetMapping("/sales")
    public ResponseEntity<List<CashMovementResponseDTO>> getMovementsBySalesByDateRange(LocalDateTime start, LocalDateTime end){
        List<CashMovementResponseDTO> movementList = service.getCashMovementsByDateRange(start, end);
        return ResponseEntity.ok(movementList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByUser(@PathVariable Long id){
        List<CashMovementResponseDTO> registerList = service.getCashMovementsByUser(id);
        return ResponseEntity.ok(registerList);
    }

    @PostMapping
    public ResponseEntity<CashMovementResponseDTO> createMovement(@RequestBody CashMovementCreateRequestDTO data) {
        CashMovementResponseDTO movement = service.createMovement(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CashMovementResponseDTO> updateMovement(@PathVariable Long id, @RequestBody UpdateCashMovementRequestDTO data){
        CashMovementResponseDTO movement = service.updateMovement(id, data);
        return ResponseEntity.ok(movement);
    }
}
