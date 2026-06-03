package com.poncheck.repository;

import com.poncheck.entity.Movement;
import com.poncheck.enums.TypeInventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findMovementByTypeInventoryMovementAndBusinessId(TypeInventoryMovement type, Long id);
    List<Movement> findMovementByTypeInventoryMovement(TypeInventoryMovement type);
    List<Movement> findMovementsByProductId(Long id);
    List<Movement> findMovementsBySale_Id(Long id);

}
