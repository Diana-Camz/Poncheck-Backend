package com.poncheck.service;


import com.poncheck.dto.request.cash.CashRegisterCloseRequestDTO;
import com.poncheck.dto.request.cash.CashRegisterOpenRequestDTO;
import com.poncheck.dto.request.cash.UpdateRegisterRequestDTO;
import com.poncheck.dto.response.cash.CashRegisterResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface CashRegisterService {
    CashRegisterResponseDTO openRegister(CashRegisterOpenRequestDTO data);
    CashRegisterResponseDTO closeRegister(Long id, CashRegisterCloseRequestDTO data);
    CashRegisterResponseDTO updateRegister(Long id, UpdateRegisterRequestDTO data);
    CashRegisterResponseDTO getCurrentRegister();
    List<CashRegisterResponseDTO> getRegistersByDateRange(LocalDateTime start, LocalDateTime end);
    List<CashRegisterResponseDTO> getRegistersOpenedByUser(Long id);
    List<CashRegisterResponseDTO> getRegistersClosedByUser(Long id);
    CashRegisterResponseDTO getRegisterById(Long id);
}
