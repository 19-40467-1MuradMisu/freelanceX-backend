package com.freelancex.biddingservice.kafka;

import com.freelancex.biddingservice.dtos.event.contract.UpdateContractEvent;
import com.freelancex.biddingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.biddingservice.dtos.event.job.UpdateJobEvent;
import com.freelancex.biddingservice.dtos.event.user.CreateUserEvent;
import com.freelancex.biddingservice.dtos.event.user.UpdateUserEvent;
import com.freelancex.biddingservice.kafka.interfaces.KafkaConsumerService;
import com.freelancex.biddingservice.services.interfaces.ContractService;
import com.freelancex.biddingservice.services.interfaces.JobService;
import com.freelancex.biddingservice.services.interfaces.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final UserService userService;
    private final JobService jobService;
    private final ContractService contractService;

    public KafkaConsumerServiceImpl(UserService userService, JobService jobService,
                                    ContractService contractService) {
        this.userService = userService;
        this.jobService = jobService;
        this.contractService = contractService;
    }

    @KafkaListener(
            topics = "${kafka.topics.user-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeUserCreatedEvent(CreateUserEvent event) {
        this.userService.createUser(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.user-updated}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeUserUpdatedEvent(UpdateUserEvent event) {
        this.userService.updateUser(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.job-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeJobCreatedEvent(CreateJobEvent event) {
        this.jobService.createJob(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.job-updated}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeJobUpdatedEvent(UpdateJobEvent event) {
        this.jobService.updateJob(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.payment.completed",
            groupId = "${spring.kafka.consumer.json.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumePaymentCompletedEvent(UpdateContractEvent event) {
        this.contractService.updateContractStatus(event);
    }
}
