package com.assignment.login.user.repository;

import com.assignment.login.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndActivated(String email, Boolean activated);
    boolean existsByPhoneAndActivated(String phone, Boolean activated);
    Optional<User> findByEmailAndActivated(String email, Boolean activated);
}
