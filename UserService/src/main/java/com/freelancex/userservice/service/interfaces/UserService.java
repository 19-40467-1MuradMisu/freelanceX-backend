package com.freelancex.userservice.service.interfaces;

import com.freelancex.userservice.dtos.api.CreateUserRequest;
import com.freelancex.userservice.dtos.api.LoginRequest;
import com.freelancex.userservice.dtos.api.UpdateUserRequest;
import com.freelancex.userservice.dtos.event.SkillVerifiedEvent;
import com.freelancex.userservice.enums.UserRole;
import com.freelancex.userservice.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    String login(LoginRequest request);
    void register(CreateUserRequest request);
    void updateSkillVerification(SkillVerifiedEvent event);
    void updateUser(UUID userId, UpdateUserRequest request);
    User getUserByEmail(String email);
    User getUserById(UUID userId);
    List<User> getAllUsers();
    List<User> getUsersByRole(UserRole role);
    void disableUser(UUID userId);
}
