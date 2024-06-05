package com.example.eventapplication.event.service;

import com.example.eventapplication.event.dto.EventDto;
import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.event.exception.EventServiceException;
import com.example.eventapplication.event.repository.EventRepository;
import com.example.eventapplication.notification.service.EventCreatedEventListener;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventCreatedEventListener eventCreatedEventListener;

    @Override
    @Transactional
    public void createEvent(EventDto eventDto) {
        EventEntity eventEntity = new EventEntity(
                eventDto.getCity(),
                eventDto.getEventDate(),
                eventDto.getEventName());
        eventRepository.save(eventEntity);
        eventCreatedEventListener.notifyEventCreated(eventEntity);
    }

    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(e -> new EventDto(
                        e.getCity(),
                        e.getEventDate().toString(),
                        e.getEventName()))
                .toList();
    }

    @Override
    public List<EventDto> getAllEventsByCity(String city) {
        return eventRepository.findAllByCityIgnoreCase(city)
                .stream()
                .map(e -> new EventDto(
                        e.getCity(),
                        e.getEventDate().toString(),
                        e.getEventName()))
                .toList();
    }

    @Override
    @Transactional
    public void deleteEvent(UUID technicalEventId) {
        Optional<EventEntity> eventEntity = eventRepository.findByTechnicalEventId(technicalEventId);
        eventEntity.orElseThrow(() -> new EventServiceException(
                "The event was not deleted because it does not exist!"));
        eventRepository.deleteByTechnicalEventId(technicalEventId);
    }

    @Override
    @Transactional
    public void updateEvent(UUID technicalEventId, EventDto eventDto) {
        Optional<EventEntity> eventEntity = eventRepository.findByTechnicalEventId(technicalEventId);
        eventEntity.orElseThrow(() -> new EventServiceException(
                "The event was not updated because it does not exist!"));
        eventEntity.get().updateEntityData(new EventEntity(
                eventDto.getCity(),
                eventDto.getEventDate(),
                eventDto.getEventName()));
        //eventRepository.save(eventEntity.get());
    }
}