package com.poncheck.service;

import com.poncheck.dto.request.sales.CancelSaleRequestDTO;
import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;

import java.util.List;

public interface SalesService {
    List<SalesResponseDTO> getAllSales();
    List<SalesResponseDTO> getSalesByCancelledTrue();
    List<SalesResponseDTO> getSalesByCancelledFalse();
    SalesResponseDTO getSaleById(Long id);
    SalesResponseDTO createSale(CreateSaleRequestDTO data);
    void cancelSale(Long id, CancelSaleRequestDTO data);
}
