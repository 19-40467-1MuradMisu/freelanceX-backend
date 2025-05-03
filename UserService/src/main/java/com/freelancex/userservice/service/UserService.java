package com.freelancex.userservice.service;

import com.freelancex.userservice.model.User;
import com.freelancex.userservice.model.User.Role;
import com.freelancex.userservice.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

public User createUser(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
        throw new IllegalArgumentException("Email already exists");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getProfile() != null) {
        user.getProfile().setUser(user);  // 👈 Important: Set the link manually
    }

    return userRepository.save(user);
}


    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

public User findById(UUID id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
}
public List<User> getAllUsers() {
    return userRepository.findAll();
}

public List<User> getUsersByRole(Role role) {
    return userRepository.findByRole(role);  // Return users based on role
}
public User loadUserByUsername(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
}

}
