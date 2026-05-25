package com.poncheck.service.impl;

import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.Sales;
import com.poncheck.repository.SalesRepository;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository repository;

    @Override
    public List<SalesResponseDTO> getAllSales(){
        List<Sales> salesList = repository.findAll();
       return salesList.stream()
               .map(SalesResponseDTO::new)
               .toList();
    }
}
