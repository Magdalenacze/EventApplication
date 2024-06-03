package com.example.eventapplication.event.service;

import com.example.eventapplication.event.dto.EventDto;

import java.util.List;
import java.util.UUID;

public interface EventService {

   void createEvent(EventDto EventDto);

    List<EventDto> getAllEvents();

    List<EventDto> getAllEventsByCity(String city);

    void deleteEvent(UUID technicalEventId);

    void updateEvent(UUID technicalEventId, EventDto EventDto);
}