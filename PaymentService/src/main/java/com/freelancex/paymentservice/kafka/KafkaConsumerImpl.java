package com.freelancex.paymentservice.kafka;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.paymentservice.kafka.interfaces.KafkaConsumer;
import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerImpl implements KafkaConsumer {
    @KafkaListener(
            topics = "${kafka.topics.contract-created}",
            groupId = "${spring.kafka.consumer.json.group-id}",
            errorHandler = "validationErrorHandler")
    @Override
    public void consumeUserCreatedEvent(@Valid CreateContractEvent event) {

    }
}
