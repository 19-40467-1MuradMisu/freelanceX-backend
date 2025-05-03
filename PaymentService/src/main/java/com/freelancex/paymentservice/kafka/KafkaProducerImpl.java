package com.freelancex.paymentservice.kafka;

import com.freelancex.paymentservice.dtos.event.payment.CompletePaymentEvent;
import com.freelancex.paymentservice.kafka.interfaces.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);

    private final KafkaTemplate<String, CompletePaymentEvent> paymentEventKafkaTemplate;

    @Value("${kafka.topics.payment-completed}")
    private String paymentCompletedTopic;

    public KafkaProducerImpl(KafkaTemplate<String, CompletePaymentEvent> template) {
        this.paymentEventKafkaTemplate = template;
    }

    @Override
    public void sendPaymentCompletedEvent(CompletePaymentEvent event) {
        sendEvent(paymentEventKafkaTemplate, paymentCompletedTopic, event.contractId().toString(), event);
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
