package com.poncheck.repository;

import com.poncheck.entity.Sales;
import com.poncheck.enums.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findByIdAndBusiness_id(Long saleId, Long BusinessId);
    List<Sales> findBySaleStatusAndBusinessId(SaleStatus status, Long id);
    List<Sales> findBySaleStatus(SaleStatus status);
    List<Sales> findByDateBetweenAndBusinessId(LocalDateTime start, LocalDateTime end, Long id);
}
