package com.freelancex.userservice.controller;

import com.freelancex.userservice.dtos.api.CreateUserRequest;
import com.freelancex.userservice.dtos.api.LoginRequest;
import com.freelancex.userservice.dtos.api.LoginResponse;
import com.freelancex.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);

        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.register(request);
        return ResponseEntity.ok("user created");
    }
}
