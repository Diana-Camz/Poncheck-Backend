package com.poncheck.controller;

import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
