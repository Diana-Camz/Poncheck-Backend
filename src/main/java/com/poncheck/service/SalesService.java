package com.poncheck.service;

import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;

import java.util.List;

public interface SalesService {
    List<SalesResponseDTO> getAllSales();
    SalesResponseDTO createSale(CreateSaleRequestDTO data);
}
