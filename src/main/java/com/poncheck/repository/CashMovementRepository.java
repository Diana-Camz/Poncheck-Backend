package com.poncheck.repository;

import com.poncheck.entity.CashMovement;
import com.poncheck.enums.TypeCashMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CashMovementRepository extends JpaRepository<CashMovement, Long> {
    List<CashMovement> findCashMovementByTypeCashMovement(TypeCashMovement type);
    List<CashMovement> findCashMovementBySale_id(Long id);
    List<CashMovement> findBySale_dateBetween(LocalDateTime start, LocalDateTime end);
    List<CashMovement> findByMovementAtBetween(LocalDateTime start, LocalDateTime end);
    List<CashMovement> findByUser_id(Long id);
}
