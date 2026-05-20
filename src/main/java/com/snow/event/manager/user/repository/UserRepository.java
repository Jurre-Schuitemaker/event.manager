package com.snow.event.manager.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snow.event.manager.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
