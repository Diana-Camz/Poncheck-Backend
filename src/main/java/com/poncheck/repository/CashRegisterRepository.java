package com.poncheck.repository;

import com.poncheck.entity.CashRegister;
import com.poncheck.enums.CashRegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
    boolean existsByStatusAndBusinessId(CashRegisterStatus status, Long id);
    Optional<CashRegister> findByStatus(CashRegisterStatus status);
    Optional<CashRegister> findByStatusAndBusinessId(CashRegisterStatus status, Long id);
    List<CashRegister> findByOpenedAtBetween(LocalDateTime start, LocalDateTime end);
    List<CashRegister> findByOpenedAtBetweenAndBusinessId(LocalDateTime start, LocalDateTime end, Long id);
    List<CashRegister> findByOpenedBy_id(Long id);
    List<CashRegister> findByClosedBy_id(Long id);
}
