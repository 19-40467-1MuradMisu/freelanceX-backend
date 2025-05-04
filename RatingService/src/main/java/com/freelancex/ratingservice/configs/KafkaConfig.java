package com.freelancex.ratingservice.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
