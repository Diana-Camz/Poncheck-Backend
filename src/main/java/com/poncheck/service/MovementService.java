package com.poncheck.service;

import com.poncheck.dto.request.inventory.CreateMovementRequestDTO;
import com.poncheck.dto.request.inventory.UpdateMovementRequestDTO;
import com.poncheck.dto.response.inventory.MovementItemResponseDTO;
import com.poncheck.dto.response.inventory.MovementResponseDTO;
import com.poncheck.enums.TypeInventoryMovement;

import java.util.List;

public interface MovementService {
    List<MovementItemResponseDTO> getMovementsByType(TypeInventoryMovement type);
    List<MovementItemResponseDTO> getMovementsByProduct(Long id);
    List<MovementItemResponseDTO> getMovementsBySale(Long id);
    MovementResponseDTO getMovementById(Long id);
    List<MovementItemResponseDTO> createMovement(CreateMovementRequestDTO data);
    MovementResponseDTO updateMovement(Long id, UpdateMovementRequestDTO data);
}
