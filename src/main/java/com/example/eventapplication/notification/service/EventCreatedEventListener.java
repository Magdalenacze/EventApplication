package com.example.eventapplication.notification.service;

import com.example.eventapplication.event.entity.EventEntity;

public interface EventCreatedEventListener {

    void notifyEventCreated(EventEntity eventEntity);
}
