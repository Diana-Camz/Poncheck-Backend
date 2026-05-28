package com.poncheck.controller;

import com.poncheck.dto.request.cash.CashRegisterCloseRequestDTO;
import com.poncheck.dto.request.cash.CashRegisterOpenRequestDTO;
import com.poncheck.dto.request.cash.UpdateRegisterRequestDTO;
import com.poncheck.dto.response.cash.CashRegisterResponseDTO;
import com.poncheck.service.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registers")
@RequiredArgsConstructor
public class CashRegisterController {

    private final CashRegisterService service;

    @PostMapping
    public ResponseEntity<CashRegisterResponseDTO> openRegister(@RequestBody CashRegisterOpenRequestDTO data){
        CashRegisterResponseDTO openCashRegister = service.openRegister(data);
        return ResponseEntity.ok(openCashRegister);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CashRegisterResponseDTO> closeRegister(@PathVariable Long id, @RequestBody CashRegisterCloseRequestDTO data){
        CashRegisterResponseDTO closeCashRegister = service.closeRegister(id, data);
        return ResponseEntity.ok(closeCashRegister);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CashRegisterResponseDTO> updateRegister(@PathVariable Long id, @RequestBody UpdateRegisterRequestDTO data){
        CashRegisterResponseDTO register = service.updateRegister(id, data);
        return ResponseEntity.ok(register);
    }

    @GetMapping("/current")
    public ResponseEntity<CashRegisterResponseDTO> getCurrentRegister(){
        CashRegisterResponseDTO register = service.getCurrentRegister();
        return ResponseEntity.ok(register);
    }

    public ResponseEntity<CashRegisterResponseDTO> getRegisterById(@PathVariable Long id){
        CashRegisterResponseDTO register = service.getRegisterById(id);
        return ResponseEntity.ok(register);
    }

    @GetMapping
    public ResponseEntity<List<CashRegisterResponseDTO>> getRegistersByDateRange(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        List<CashRegisterResponseDTO> registerList = service.getRegistersByDateRange(start, end);
        return ResponseEntity.ok(registerList);
    }

    @GetMapping("/opened/user/{id}")
    public ResponseEntity<List<CashRegisterResponseDTO>> getRegistersOpenedByUser(@PathVariable Long id){
        List<CashRegisterResponseDTO> registerList = service.getRegistersOpenedByUser(id);
        return ResponseEntity.ok(registerList);
    }

    @GetMapping("/closed/user/{id}")
    public ResponseEntity<List<CashRegisterResponseDTO>> getRegistersClosedByUser(@PathVariable Long id){
        List<CashRegisterResponseDTO> registerList = service.getRegistersClosedByUser(id);
        return ResponseEntity.ok(registerList);
    }
 }
