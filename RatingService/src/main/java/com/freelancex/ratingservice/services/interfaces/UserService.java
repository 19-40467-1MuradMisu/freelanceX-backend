package com.freelancex.ratingservice.services.interfaces;

import com.freelancex.ratingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.ratingservice.dtos.event.user.UpdateUserEvent;

public interface UserService {
    void createUser(CreateUserEvent event);
    void updateUser(UpdateUserEvent event);
}
