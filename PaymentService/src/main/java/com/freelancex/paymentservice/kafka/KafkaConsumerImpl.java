package com.freelancex.paymentservice.kafka;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.paymentservice.kafka.interfaces.KafkaConsumer;
import com.freelancex.paymentservice.services.interfaces.PaymentService;
import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerImpl implements KafkaConsumer {

    private final PaymentService paymentService;

    public KafkaConsumerImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(
            topics = "${kafka.topics.contract-created}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "validationErrorHandler")
    @Override
    public void consumeContractCreatedEvent(@Valid CreateContractEvent event) {
        paymentService.createPayment(event);
    }
}
