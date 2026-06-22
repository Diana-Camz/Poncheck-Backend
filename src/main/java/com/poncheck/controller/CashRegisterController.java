package com.poncheck.controller;

import com.poncheck.dto.request.cash.CashRegisterCloseRequestDTO;
import com.poncheck.dto.request.cash.CashRegisterOpenRequestDTO;
import com.poncheck.dto.request.cash.UpdateRegisterRequestDTO;
import com.poncheck.dto.response.cash.CashRegisterResponseDTO;
import com.poncheck.service.CashRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Cash Registers", description = "Endpoints for managing cash registers, including opening, closing, updates, and register history tracking.")
@RestController
@RequestMapping("/registers")
@RequiredArgsConstructor
public class CashRegisterController {

    private final CashRegisterService service;

    @Operation(summary = "Open cash register", description = "Opens a new cash register for the current business with the specified opening amount.")
    @PostMapping
    public ResponseEntity<CashRegisterResponseDTO> openRegister(@RequestBody CashRegisterOpenRequestDTO data){
        CashRegisterResponseDTO openCashRegister = service.openRegister(data);
        return ResponseEntity.ok(openCashRegister);
    }

    @Operation(summary = "Close cash register", description = "Closes an open cash register, calculates the cash difference, and records the final balance.")
    @PostMapping("/{id}")
    public ResponseEntity<CashRegisterResponseDTO> closeRegister(@PathVariable Long id, @RequestBody CashRegisterCloseRequestDTO data){
        CashRegisterResponseDTO closeCashRegister = service.closeRegister(id, data);
        return ResponseEntity.ok(closeCashRegister);
    }

    @Operation(summary = "Update cash register", description = "Updates editable information of an existing cash register.")
    @PatchMapping("/{id}")
    public ResponseEntity<CashRegisterResponseDTO> updateRegister(@PathVariable Long id, @RequestBody UpdateRegisterRequestDTO data){
        CashRegisterResponseDTO register = service.updateRegister(id, data);
        return ResponseEntity.ok(register);
    }

    @Operation(summary = "Get current cash register", description = "Retrieves the currently open cash register for the current business.")
    @GetMapping("/current")
    public ResponseEntity<CashRegisterResponseDTO> getCurrentRegister(){
        CashRegisterResponseDTO register = service.getCurrentRegister();
        return ResponseEntity.ok(register);
    }

    @Operation(summary = "Get cash register by ID", description = "Retrieves detailed information about a specific cash register.")
    @GetMapping("/{id}")
    public ResponseEntity<CashRegisterResponseDTO> getRegisterById(@PathVariable Long id){
        CashRegisterResponseDTO register = service.getRegisterById(id);
        return ResponseEntity.ok(register);
    }

    @Operation(summary = "Get cash registers by date range", description = "Retrieves all cash registers opened within the specified date range.")
    @GetMapping("/date-range")
    public ResponseEntity<List<CashRegisterResponseDTO>> getRegistersByDateRange(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        List<CashRegisterResponseDTO> registerList = service.getRegistersByDateRange(start, end);
        return ResponseEntity.ok(registerList);
    }

    @Operation(summary = "Get registers opened by user", description = "Retrieves all cash registers opened by a specific user.")
    @GetMapping("/opened/user/{id}")
    public ResponseEntity<List<CashRegisterResponseDTO>> getRegistersOpenedByUser(@PathVariable Long id){
        List<CashRegisterResponseDTO> registerList = service.getRegistersOpenedByUser(id);
        return ResponseEntity.ok(registerList);
    }

    @Operation(summary = "Get registers closed by user", description = "Retrieves all cash registers closed by a specific user.")
    @GetMapping("/closed/user/{id}")
    public ResponseEntity<List<CashRegisterResponseDTO>> getRegistersClosedByUser(@PathVariable Long id){
        List<CashRegisterResponseDTO> registerList = service.getRegistersClosedByUser(id);
        return ResponseEntity.ok(registerList);
    }
 }
