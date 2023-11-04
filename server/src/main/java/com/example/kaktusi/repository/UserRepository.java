package com.example.kaktusi.repository;

import com.example.kaktusi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
