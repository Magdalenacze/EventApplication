package com.example.eventapplication.notification.service;

import com.example.eventapplication.event.entity.EventEntity;

public interface NotificationService {

    void createNotification(EventEntity eventEntity);
}