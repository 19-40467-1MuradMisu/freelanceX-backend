package com.freelancex.userservice.controller;

import com.freelancex.userservice.Jwt.JwtService;
import com.freelancex.userservice.dtos.AuthRequest;
import com.freelancex.userservice.model.User;
import com.freelancex.userservice.model.User.Role;
import com.freelancex.userservice.service.UserService;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        System.out.println(user);
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

 @GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(user);
}
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUser(email);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final User user = userService.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
    }


 @GetMapping
public ResponseEntity<?> getAllUsers(@RequestParam(required = false) Role role) {
    if (role != null) {  // Correct the check here to 'role' instead of 'type'
        return ResponseEntity.ok(userService.getUsersByRole(role));  // Pass 'role' to the service method
    }
    return ResponseEntity.ok(userService.getAllUsers());
}
}