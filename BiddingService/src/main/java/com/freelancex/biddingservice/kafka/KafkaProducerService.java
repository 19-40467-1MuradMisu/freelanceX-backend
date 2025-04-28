package com.freelancex.biddingservice.kafka;

import com.freelancex.biddingservice.dtos.event.contract.CreateContractEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${kafka.topics.contract-created}")
    private String contractCreatedTopic;

    public KafkaProducerService(
            @Qualifier("jsonKafkaTemplate") KafkaTemplate<String, Object> jsonKafkaTemplate) {
        this.kafkaTemplate = jsonKafkaTemplate;
    }

    public void sendContractCreatedEvent(CreateContractEvent event) {
        kafkaTemplate.send(contractCreatedTopic, event.getContractId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Sent {} event: {}", contractCreatedTopic, event.getContractId());
                    } else {
                        logger.error("Failed to send {} event: {}, error:{}", contractCreatedTopic,
                                event.getContractId(), ex.getMessage());
                    }
                });
    }
}
