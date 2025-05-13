package com.freelancex.userservice.controller;

import com.freelancex.userservice.dtos.api.UpdateUserRequest;
import com.freelancex.userservice.enums.UserRole;
import com.freelancex.userservice.model.User;
import com.freelancex.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("X-User-Id") String userId) {
        User user = userService.getUserById(UUID.fromString(userId));
        return ResponseEntity.ok(user);
    }

    
    @PatchMapping("/update-profile")
    public ResponseEntity<Void> updateUserProfile(@RequestBody UpdateUserRequest request,
                                                 @RequestHeader("X-User-Id") String userId) {
        userService.updateUser(UUID.fromString(userId), request);
        return ResponseEntity.ok().build();
    }
    
}
