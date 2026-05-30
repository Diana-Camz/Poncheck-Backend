package com.poncheck.service;

import com.poncheck.dto.request.cash.CashMovementCreateRequestDTO;
import com.poncheck.dto.request.cash.UpdateCashMovementRequestDTO;
import com.poncheck.dto.response.cash.CashMovementResponseDTO;
import com.poncheck.enums.TypeCashMovement;

import java.time.LocalDateTime;
import java.util.List;

public interface CashMovementService {
    List<CashMovementResponseDTO> getMovementsByType(TypeCashMovement type);
    List<CashMovementResponseDTO> getMovementsBySale(Long id);
    CashMovementResponseDTO getMovementById(Long id);
    List<CashMovementResponseDTO> getCashMovementsByDateRange(LocalDateTime start, LocalDateTime end);
    List<CashMovementResponseDTO> getCashMovementsByUser(Long id);
    CashMovementResponseDTO createMovement(CashMovementCreateRequestDTO data);
    CashMovementResponseDTO updateMovement(Long id, UpdateCashMovementRequestDTO data);
    List<CashMovementResponseDTO> getMovementsBySalesByDateRange(LocalDateTime start, LocalDateTime end);
}
