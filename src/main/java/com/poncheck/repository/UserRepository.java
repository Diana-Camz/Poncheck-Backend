package com.poncheck.repository;

import com.poncheck.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();
    Optional<User> findByUsername(String email);
    boolean existsUserByUsername(String username);
}
