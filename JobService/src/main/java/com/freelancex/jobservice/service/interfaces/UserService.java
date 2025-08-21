package com.freelancex.jobservice.service.interfaces;

import com.freelancex.jobservice.dtos.event.user.CreateUserEvent;

public interface UserService {
    void createUser(CreateUserEvent event);
}
