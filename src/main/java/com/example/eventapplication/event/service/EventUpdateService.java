package com.example.eventapplication.event.service;

import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.notification.entity.NotificationEntity;

import java.util.List;

public interface EventUpdateService {

    void updateNotificationsForEvent(List<NotificationEntity> notificationEntity, EventEntity eventEntity);
}
