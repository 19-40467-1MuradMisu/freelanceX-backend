package com.freelancex.jobservice.kafka;

import com.freelancex.jobservice.dtos.event.job.CreateJobEvent;

import com.freelancex.jobservice.dtos.event.user.CreateUserEvent;
import com.freelancex.jobservice.services.JobServiceImpl;
import com.freelancex.jobservice.services.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl  {
    @Autowired()
    private final UserServiceImpl userService;

    public KafkaConsumerServiceImpl(UserServiceImpl userService
                                    ) {
        this.userService = userService;
    }

    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")

    public void consumeUserCreatedEvent(@Valid CreateUserEvent event) {
        this.userService.createUser(event);
    }
}
