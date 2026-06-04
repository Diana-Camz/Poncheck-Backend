package com.poncheck.repository;

import com.poncheck.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndBusiness_id(Long userId, Long businessId);
    List<User> findByActiveTrueAndBusinessId(Long userId);
    List<User> findByActiveFalseAndBusinessId(Long userId);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
