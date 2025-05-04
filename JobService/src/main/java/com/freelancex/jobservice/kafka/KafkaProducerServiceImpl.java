package com.freelancex.jobservice.kafka;



import com.freelancex.jobservice.dtos.event.job.CreateJobEvent;
import com.freelancex.jobservice.dtos.event.job.updateJobEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaTemplate<String, CreateJobEvent> createJobEventKafkaTemplate;
    private final KafkaTemplate<String, updateJobEvent> updateJobEventKafkaTemplate;

    @Value("${kafka.topics.job-created}")
    private String CreatedJobTopic;


    @Value("${kafka.topics.job-updated}")
    private String UpdatedJobTopic;

    public KafkaProducerServiceImpl(KafkaTemplate<String, CreateJobEvent> template,KafkaTemplate<String, updateJobEvent> template2) {
        this.createJobEventKafkaTemplate = template;
        this.updateJobEventKafkaTemplate = template2;
    }

    


    public void sendJobCreatedEvent( CreateJobEvent event) {
        sendEvent(createJobEventKafkaTemplate, CreatedJobTopic, event.jobId().toString(), event);
    }
    public void sendJobUpdatedEvent( updateJobEvent event) {
        sendEvent(updateJobEventKafkaTemplate, UpdatedJobTopic, event.jobId().toString(), event);
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