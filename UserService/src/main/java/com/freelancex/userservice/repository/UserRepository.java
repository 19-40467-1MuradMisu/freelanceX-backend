package com.freelancex.userservice.repository;

import com.freelancex.userservice.enums.UserRole;
import com.freelancex.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);
}
