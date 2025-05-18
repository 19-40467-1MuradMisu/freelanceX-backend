package com.freelancex.jobservice.kafka;

import com.freelancex.jobservice.dtos.event.contract.CompletedContractEvent;
import com.freelancex.jobservice.dtos.event.job.CreateJobEvent;

import com.freelancex.jobservice.dtos.event.user.CreateUserEvent;
import com.freelancex.jobservice.service.impl.JobServiceImpl;
import com.freelancex.jobservice.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl  {

    private final UserServiceImpl userService;
    private final JobServiceImpl jobService;

    public KafkaConsumerServiceImpl(UserServiceImpl userService, JobServiceImpl jobService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "validationErrorHandler")

    public void consumeUserCreatedEvent(@Valid CreateUserEvent event) {
        this.userService.createUser(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.contract-completed}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "validationErrorHandler")

    public void consumeContractCompletedEvent(@Valid CompletedContractEvent event) {
        this.jobService.closeJob(event);
    }
}
