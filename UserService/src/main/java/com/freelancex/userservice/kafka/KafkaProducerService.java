package com.freelancex.userservice.kafka;

import com.freelancex.userservice.dtos.event.CreateUserEvent;
import com.freelancex.userservice.dtos.event.UpdateUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, CreateUserEvent> userCreateTemplate;
    private final KafkaTemplate<String, UpdateUserEvent> userUpdateTemplate;

    @Value("${kafka.topics.user-created}")
    private String userCreatedTopic;

    @Value("${kafka.topics.user-updated}")
    private String userUpdatedTopic;

    public KafkaProducerService(KafkaTemplate<String, CreateUserEvent> createTemplate,
                                KafkaTemplate<String, UpdateUserEvent> updateTemplate) {
        userCreateTemplate = createTemplate;
        userUpdateTemplate = updateTemplate;
    }

    public void sendUserCreatedEvent(CreateUserEvent event) {
        sendEvent(userCreateTemplate, userCreatedTopic, event.userId().toString(), event);
    }

    public void sendUserUpdatedEvent(UpdateUserEvent event) {
        sendEvent(userUpdateTemplate, userUpdatedTopic, event.userId().toString(), event);
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
