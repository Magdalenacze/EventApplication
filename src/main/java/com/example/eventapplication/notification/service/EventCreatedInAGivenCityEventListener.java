package com.example.eventapplication.notification.service;

import com.example.eventapplication.event.entity.EventEntity;

public interface EventCreatedInAGivenCityEventListener {

    void notifyEventCreatedInAGivenCity(EventEntity eventEntity);
}
