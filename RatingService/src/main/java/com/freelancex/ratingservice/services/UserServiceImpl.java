package com.freelancex.ratingservice.services;

import com.freelancex.ratingservice.dtos.event.user.CreateUserEvent;



import com.freelancex.ratingservice.models.User;
import com.freelancex.ratingservice.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl  {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

       @Autowired
    public UserServiceImpl(UserRepository userRepository) {
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
}
