package com.freelancex.userservice.kafka;

import com.freelancex.userservice.dtos.event.CreateUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, CreateUserEvent> userEventKafkaTemplate;

    @Value("${kafka.topics.user-created}")
    private String userCreatedTopic;

    public KafkaProducerService(KafkaTemplate<String, CreateUserEvent> template) {
        this.userEventKafkaTemplate = template;
    }

    public void sendUserCreatedEvent(CreateUserEvent event) {
        sendEvent(userEventKafkaTemplate, userCreatedTopic, event.userId().toString(), event);
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
