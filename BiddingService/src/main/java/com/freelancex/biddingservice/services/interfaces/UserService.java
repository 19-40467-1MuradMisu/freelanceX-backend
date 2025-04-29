package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.biddingservice.dtos.event.user.UpdateUserEvent;

public interface UserService {
    void createUser(CreateUserEvent event);

    void updateUser(UpdateUserEvent event);
}
