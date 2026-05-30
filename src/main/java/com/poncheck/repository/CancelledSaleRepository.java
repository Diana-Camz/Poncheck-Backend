package com.poncheck.repository;

import com.poncheck.entity.CancelledSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelledSaleRepository extends JpaRepository<CancelledSale, Long> {
}
