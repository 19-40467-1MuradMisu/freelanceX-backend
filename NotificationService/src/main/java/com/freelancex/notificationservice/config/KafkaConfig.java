package com.freelancex.notificationservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    private final static Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public KafkaListenerErrorHandler validationErrorHandler() {
        return (message, exception) -> {
            if (exception.getCause() instanceof MethodArgumentNotValidException validationException) {
                // Log validation errors
                validationException.getBindingResult().getAllErrors().forEach(error ->
                        logger.error("Validation error: {}", error.getDefaultMessage()));
            } else {
                logger.error("Error processing Kafka message: {}", exception.getMessage());
            }
            return false;
        };
    }
}