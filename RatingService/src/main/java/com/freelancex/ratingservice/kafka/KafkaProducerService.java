package com.freelancex.ratingservice.kafka;

import com.freelancex.ratingservice.dtos.event.rating.CreateRatingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, CreateRatingEvent> ratingEventKafkaTemplate;

    @Value("${kafka.topics.review-created}")
    private String contractCreatedTopic;

    public KafkaProducerService(KafkaTemplate<String, CreateRatingEvent> template) {
        this.ratingEventKafkaTemplate = template;
    }

    public void sendRatingCreatedEvent(CreateRatingEvent event) {
        sendEvent(ratingEventKafkaTemplate, contractCreatedTopic, event.userId().toString(), event);
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