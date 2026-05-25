package com.poncheck.controller;

import com.poncheck.dto.request.sales.CancelSaleRequestDTO;
import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.Sales;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SalesService service;

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales(){
        List<SalesResponseDTO> salesList = service.getAllSales();
        return ResponseEntity.ok(salesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> getSaleById(@PathVariable Long id){
        SalesResponseDTO sale = service.getSaleById(id);
        return ResponseEntity.ok(sale);
    }

    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(@RequestBody CreateSaleRequestDTO data){
        SalesResponseDTO sale = service.createSale(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id, @RequestBody CancelSaleRequestDTO data){
        service.cancelSale(id, data);
        return ResponseEntity.noContent().build();
    }
}
