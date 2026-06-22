package com.poncheck.repository;

import com.poncheck.entity.Category;
import com.poncheck.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndBusiness_id(Long categoryId, Long businessId);
    List<Category> findByActiveTrueAndBusinessId(Long businessId);
    List<Category> findByActiveFalseAndBusinessId(Long businessId);
    boolean existsByNameIgnoreCaseAndBusinessId(String name, Long businessId);
}
