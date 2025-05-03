package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.biddingservice.dtos.event.user.UpdateUserEvent;
import com.freelancex.biddingservice.enums.UserRole;
import com.freelancex.biddingservice.models.Client;
import com.freelancex.biddingservice.models.Freelancer;
import com.freelancex.biddingservice.models.User;
import com.freelancex.biddingservice.repositories.UserRepository;
import com.freelancex.biddingservice.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void createUser(CreateUserEvent event) {
        if (event.role() == UserRole.CLIENT) {
            Client client = new Client();
            client.setUserId(event.userId());
            client.setFirstName(event.firstName());
            client.setLastName(event.lastName());

            userRepository.save(client);
        } else {
            Freelancer freelancer = new Freelancer();
            freelancer.setUserId(event.userId());
            freelancer.setFirstName(event.firstName());
            freelancer.setLastName(event.lastName());

            userRepository.save(freelancer);
        }
        logger.info("User created: {}", event.userId());
    }

    @Override
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
