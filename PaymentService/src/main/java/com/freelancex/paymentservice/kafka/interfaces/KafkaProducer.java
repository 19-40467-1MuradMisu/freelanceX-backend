package com.freelancex.paymentservice.kafka.interfaces;

import com.freelancex.paymentservice.dtos.event.payment.CompletePaymentEvent;

public interface KafkaProducer {
    void sendPaymentCompletedEvent(CompletePaymentEvent event);
}
