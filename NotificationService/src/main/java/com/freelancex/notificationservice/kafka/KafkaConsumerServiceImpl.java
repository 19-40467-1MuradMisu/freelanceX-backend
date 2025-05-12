package com.freelancex.notificationservice.kafka;

import com.freelancex.notificationservice.dtos.contract.CreateContractEvent;
import com.freelancex.notificationservice.dtos.rating.CreateRatingEvent;
import com.freelancex.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl {

    private final NotificationService notificationService;

    public KafkaConsumerServiceImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${kafka.topics.contract-created}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeContractCreatedEvent(CreateContractEvent event) {
        String content = String.format(
                "Contract of $%.2f has been created.",
                event.amount()
        );
        notificationService.processEventNotification("contract_created", event.userId(), content);
    }

    @KafkaListener(
            topics = "${kafka.topics.rating-created}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeRatingCreatedEvent(CreateRatingEvent event) {
        String content = String.format(
                "You got %d star review.", event.score().getValue()
        );
        notificationService.processEventNotification("rating_created", event.userId(), content);
    }
}
