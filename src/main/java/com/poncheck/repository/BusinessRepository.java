package com.poncheck.repository;

import com.poncheck.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByIdAndOwner_id(Long businessId, Long ownerId);
    List<Business> findByActiveTrue();
    List<Business> findByActiveFalse();
    List<Business> findBusinessByOwner_id(Long ownerId);
    long countByName(String name);
}
