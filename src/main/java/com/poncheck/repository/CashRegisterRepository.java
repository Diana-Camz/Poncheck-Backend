package com.poncheck.repository;

import com.poncheck.entity.CashRegister;
import com.poncheck.enums.CashRegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
    boolean existsByStatus(CashRegisterStatus status);
    Optional<CashRegister> findByStatus(CashRegisterStatus status);
    List<CashRegister> findByOpenedAtBetween(LocalDateTime start, LocalDateTime end);
    List<CashRegister> findByOpenedBy_id(Long id);
    List<CashRegister> findByClosedBy_id(Long id);
}
