package com.poncheck.repository;

import com.poncheck.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByActiveTrue();
    boolean existsUserByUsername(String username);
}
