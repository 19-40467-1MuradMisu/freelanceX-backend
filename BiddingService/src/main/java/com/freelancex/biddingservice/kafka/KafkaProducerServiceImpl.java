package com.freelancex.biddingservice.kafka;

import com.freelancex.biddingservice.dtos.event.contract.CompletedContractEvent;
import com.freelancex.biddingservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.biddingservice.kafka.interfaces.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaTemplate<String, CreateContractEvent> contractCreatedTemplate;
    private final KafkaTemplate<String, CompletedContractEvent> contractCompletedTemplate;

    @Value("${kafka.topics.contract-created}")
    private String contractCreatedTopic;

    @Value("${kafka.topics.contract-completed}")
    private String contractCompletedTopic;

    public KafkaProducerServiceImpl(KafkaTemplate<String, CreateContractEvent> template1,
                                    KafkaTemplate<String, CompletedContractEvent> template2) {
        this.contractCreatedTemplate = template1;
        this.contractCompletedTemplate = template2;
    }

    @Override
    public void sendContractCreatedEvent(CreateContractEvent event) {
        sendEvent(contractCreatedTemplate, contractCreatedTopic, event.contractId().toString(), event);
    }

    @Override
    public void sendContractCompletedEvent(CompletedContractEvent event) {
        sendEvent(contractCompletedTemplate, contractCompletedTopic, event.jobId().toString(), event);
    }

    private <T> void sendEvent(KafkaTemplate<String, T> template, String topic, String key, T event) {
        template.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Sent {} event with key {}", topic, key);
                    } else {
                        logger.error("Failed to send {} event with key {}: {}", topic, key, ex.getMessage(),
                                ex);
                    }
                });
    }
}