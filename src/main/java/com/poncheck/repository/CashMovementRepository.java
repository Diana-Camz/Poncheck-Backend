package com.poncheck.repository;

import com.poncheck.entity.CashMovement;
import com.poncheck.enums.TypeCashMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CashMovementRepository extends JpaRepository<CashMovement, Long> {
    List<CashMovement> findCashMovementByTypeCashMovement(TypeCashMovement type);
    List<CashMovement> findCashMovementByTypeCashMovementAndBusiness_id(TypeCashMovement type, Long id);
    List<CashMovement> findCashMovementBySaleId(Long id);
    List<CashMovement> findCashMovementBySaleIdAndBusiness_id(Long id, Long businessId);
    List<CashMovement> findBySale_dateBetweenAndBusiness_id(LocalDateTime start, LocalDateTime end, Long id);
    List<CashMovement> findByMovementAtBetweenAndBusiness_id(LocalDateTime start, LocalDateTime end, Long id);
    List<CashMovement> findByMovementAtBetween(LocalDateTime start, LocalDateTime end);
    List<CashMovement> findByUser_id(Long id);
    List<CashMovement> findByUser_idAndBusiness_id(Long id, Long businessId);

}
