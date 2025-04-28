package com.freelancex.biddingservice.kafka;

import com.freelancex.biddingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.biddingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.biddingservice.services.interfaces.JobService;
import com.freelancex.biddingservice.services.interfaces.UserService;
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
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserCreatedEvent(CreateUserEvent event) {

    }

    @KafkaListener(
            topics = "${kafka.topics.job-created}",
            groupId = "${spring.kafka.consumer.string.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeJobCreatedEvent(CreateJobEvent event) {

    }
}
