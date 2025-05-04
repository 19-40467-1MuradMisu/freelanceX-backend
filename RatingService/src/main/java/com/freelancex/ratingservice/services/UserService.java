package com.freelancex.ratingservice.services;

import com.freelancex.ratingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.ratingservice.dtos.event.user.UpdateUserEvent;
import com.freelancex.ratingservice.models.User;
import com.freelancex.ratingservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createUser(CreateUserEvent event) {
        User user = new User();
        user.setUserId(event.userId());
        user.setFirstName(event.firstName());
        user.setLastName(event.lastName());
        user.setRole(event.role());

        userRepository.save(user);
        logger.info("User created: {}", event.userId());
    }

    public void updateUser(UpdateUserEvent event) {
        Optional<User> user = userRepository.findById(event.userId());
        if (user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setFirstName(event.firstName());
            userToUpdate.setLastName(event.lastName());
            userRepository.save(userToUpdate);
            logger.info("User updated: {}", userToUpdate.getUserId());
        }
    }
}
