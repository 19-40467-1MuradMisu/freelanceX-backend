package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(UUID id);

    User saveUser(User user);

    void deleteUser(UUID id);
}
