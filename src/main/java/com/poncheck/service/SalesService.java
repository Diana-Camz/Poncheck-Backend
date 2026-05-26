package com.poncheck.service;

import com.poncheck.dto.request.sales.CancelSaleRequestDTO;
import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.request.sales.UpdateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.enums.SaleStatus;

import java.util.List;

public interface SalesService {
    List<SalesResponseDTO> getAllSales();
    List<SalesResponseDTO> getSalesByStatus(SaleStatus status);
    SalesResponseDTO getSaleById(Long id);
    SalesResponseDTO createSale(CreateSaleRequestDTO data);
    SalesResponseDTO updateSale(Long id, UpdateSaleRequestDTO data);
    void cancelSale(Long id, CancelSaleRequestDTO data);
}
