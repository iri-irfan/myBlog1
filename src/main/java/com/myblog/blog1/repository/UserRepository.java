package com.myblog.blog1.repository;


import com.myblog.blog1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String email, String username);
    Optional<User> findByusername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

}

