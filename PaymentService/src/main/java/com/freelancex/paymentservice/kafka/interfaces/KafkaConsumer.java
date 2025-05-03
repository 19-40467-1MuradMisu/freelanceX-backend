package com.freelancex.paymentservice.kafka.interfaces;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;
import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;

public interface KafkaConsumer {
    @KafkaListener(
            topics = "${kafka.topics.contract-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    void consumeUserCreatedEvent(@Valid CreateContractEvent event);
}
