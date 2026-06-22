package com.poncheck.repository;

import com.poncheck.entity.Movement;
import com.poncheck.entity.Sales;
import com.poncheck.enums.TypeInventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    Optional<Movement> findByIdAndBusiness_id(Long movementId, Long businessId);
    List<Movement> findMovementByTypeInventoryMovementAndBusinessId(TypeInventoryMovement type, Long businessId);
    List<Movement> findMovementByTypeInventoryMovement(TypeInventoryMovement type);
    List<Movement> findMovementsByProductIdAndBusiness_id(Long saleId, Long businessId);
    List<Movement> findMovementsBySale_idAndBusiness_id(Long saleId, Long businessId);

}
