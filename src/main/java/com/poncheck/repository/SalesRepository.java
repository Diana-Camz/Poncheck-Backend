package com.poncheck.repository;

import com.poncheck.entity.Sales;
import com.poncheck.enums.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findBySaleStatus(SaleStatus status);
}
