package com.freelancex.userservice.kafka;

import com.freelancex.userservice.dtos.event.SkillVerifiedEvent;
import com.freelancex.userservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final UserService userService;

    public KafkaConsumerService(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(
            topics = "${kafka.topics.skill-verified}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "validationErrorHandler")

    public void consumeSkillVerifiedEvent(SkillVerifiedEvent event) {
        this.userService.updateSkillVerification(event);
    }
}
