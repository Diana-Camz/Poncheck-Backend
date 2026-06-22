package com.poncheck.repository;

import com.poncheck.entity.CancelledSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CancelledSaleRepository extends JpaRepository<CancelledSale, Long> {
    Optional<CancelledSale> findByIdAndBusiness_id(Long cancelledSaleId, Long businessId);
}
