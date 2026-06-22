package com.poncheck.controller;

import com.poncheck.dto.request.sales.CancelSaleRequestDTO;
import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.request.sales.UpdateSaleRequestDTO;
import com.poncheck.dto.response.cash.CashRegisterResponseDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.Sales;
import com.poncheck.enums.SaleStatus;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Sales", description = "Endpoints for managing sales, including creation, updates, cancellation, and retrieval by status or date range.")
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SalesService service;
    {/*    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales(){
        List<SalesResponseDTO> salesList = service.getAllSales();
        return ResponseEntity.ok(salesList);
    }
    */}

    @Operation(summary = "Get sale by ID", description = "Retrieves a specific sale by its identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> getSaleById(@PathVariable Long id){
        SalesResponseDTO sale = service.getSaleById(id);
        return ResponseEntity.ok(sale);
    }

    @Operation(summary = "Get sales by status", description = "Retrieves all sales that match the provided sale status.")
    @GetMapping("/status")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByStatus(@RequestParam SaleStatus status){
        List<SalesResponseDTO> salesList = service.getSalesByStatus(status);
        return ResponseEntity.ok(salesList);
    }

    @Operation(summary = "Get sales by date range", description = "Retrieves all sales created within the specified date range.")
    @GetMapping("/date-range")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByDateRange(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        List<SalesResponseDTO> registerList = service.getSalesByDateRange(start, end);
        return ResponseEntity.ok(registerList);
    }

    @Operation(summary = "Create sale", description = "Creates a new sale, generates inventory movements, and registers the corresponding cash movement.")
    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(@RequestBody CreateSaleRequestDTO data){
        SalesResponseDTO sale = service.createSale(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @Operation(summary = "Update sale", description = "Updates editable sale information. Cancelled sales cannot be modified.")
    @PatchMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> updateSale(@PathVariable Long id, @RequestBody UpdateSaleRequestDTO data){
        SalesResponseDTO sale = service.updateSale(id, data);
        return ResponseEntity.ok(sale);
    }

    @Operation(summary = "Cancel sale", description = "Cancels a sale, restores product stock, and generates the corresponding inventory and cash register adjustments.")
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id, @RequestBody CancelSaleRequestDTO data){
        service.cancelSale(id, data);
        return ResponseEntity.noContent().build();
    }
}
