package com.poncheck.repository;

import com.poncheck.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByActiveTrue();
    List<Business> findByActiveFalse();
    List<Business> findBusinessByOwner_id(Long ownerId);
    long countByName(String name);
}
