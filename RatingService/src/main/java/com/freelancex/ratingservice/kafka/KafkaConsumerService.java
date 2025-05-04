package com.freelancex.ratingservice.kafka;

import com.freelancex.ratingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.ratingservice.dtos.event.job.updateJobEvent;
import com.freelancex.ratingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.ratingservice.dtos.event.user.UpdateUserEvent;
import com.freelancex.ratingservice.services.JobService;
import com.freelancex.ratingservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final UserService userService;
    private final JobService jobService;

    public KafkaConsumerService(UserService userService, JobService jobService) {
        this.userService = userService;
        this.jobService = jobService;
    }

    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    public void consumeUserCreatedEvent(@Valid CreateUserEvent event) {
        this.userService.createUser(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.user-updated}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler"
    )
    public void consumeUserUpdatedEvent(@Valid UpdateUserEvent event) {
        this.userService.updateUser(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.job-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    public void consumeJobCreatedEvent(@Valid CreateJobEvent event) {
        this.jobService.createJob(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.job-updated}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    public void consumeJobUpdatedEvent(@Valid updateJobEvent event) {
        this.jobService.updateJob(event);
    }
}
