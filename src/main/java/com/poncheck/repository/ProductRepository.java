package com.poncheck.repository;

import com.poncheck.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndBusiness_id(Long id, Long businessId);
    List<Product> findByActiveTrue();
    List<Product> findByActiveTrueAndBusinessId(Long id);
    List<Product> findByActiveFalse();
    List<Product> findByActiveFalseAndBusinessId(Long id);
    long countByCategoryId(Long id);
    boolean existsByCodeAndBusinessId(String code, Long id);
}
