package com.freelancex.notificationservice.service.impl;

import com.freelancex.notificationservice.model.Notification;
import com.freelancex.notificationservice.repository.NotificationLogRepository;
import com.freelancex.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationLogRepository repository;

    public NotificationServiceImpl(NotificationLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Notification> getUserNotifications(UUID userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public void processEventNotification(String eventType, UUID userId, String content) {
        Notification notificationLog = new Notification();
        notificationLog.setUserId(userId);
        notificationLog.setType(eventType);
        notificationLog.setContent(content);
        repository.save(notificationLog);
    }
}

