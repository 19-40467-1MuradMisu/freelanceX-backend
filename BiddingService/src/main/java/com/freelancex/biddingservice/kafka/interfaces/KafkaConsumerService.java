package com.freelancex.biddingservice.kafka.interfaces;

import com.freelancex.biddingservice.dtos.event.contract.UpdateContractEvent;
import com.freelancex.biddingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.biddingservice.dtos.event.job.UpdateJobEvent;
import com.freelancex.biddingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.biddingservice.dtos.event.user.UpdateUserEvent;
import org.springframework.kafka.annotation.KafkaListener;

public interface KafkaConsumerService {
    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    void consumeUserCreatedEvent(CreateUserEvent event);

    @KafkaListener(
            topics = "${kafka.topics.user-updated}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    void consumeUserUpdatedEvent(UpdateUserEvent event);

    @KafkaListener(
            topics = "${kafka.topics.job-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    void consumeJobCreatedEvent(CreateJobEvent event);

    @KafkaListener(
            topics = "${kafka.topics.job-updated}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    void consumeJobUpdatedEvent(UpdateJobEvent event);

    @KafkaListener(
            topics = "${kafka.topics.payment-completed}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    void consumePaymentCompletedEvent(UpdateContractEvent event);
}
