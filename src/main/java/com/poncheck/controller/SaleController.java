package com.poncheck.controller;

import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
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
    private final SalesService salesService;

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales(){
        List<SalesResponseDTO> salesList = salesService.getAllSales();
        return ResponseEntity.ok(salesList);
    }


    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(@RequestBody CreateSaleRequestDTO data){
        SalesResponseDTO sale = salesService.createSale(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }
}
