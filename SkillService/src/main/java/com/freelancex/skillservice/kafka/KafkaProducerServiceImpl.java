package com.freelancex.skillservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.freelancex.skillservice.dtos.SkillVerifiedEvent;

@Service
public class KafkaProducerServiceImpl{
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaTemplate<String, SkillVerifiedEvent> skillEventKafkaTemplate;

    @Value("${kafka.topics.skill-verified}")
    private String skillVerifiedTopic;

    public KafkaProducerServiceImpl(KafkaTemplate<String, SkillVerifiedEvent> template) {
        this.skillEventKafkaTemplate = template;
    }

    public void sendSkillVerifiedEvent(SkillVerifiedEvent event) {
        sendEvent(skillEventKafkaTemplate, skillVerifiedTopic, event.getUserId().toString(), event);
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