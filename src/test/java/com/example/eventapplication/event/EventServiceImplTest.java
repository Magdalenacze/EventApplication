package com.example.eventapplication.event;

import com.example.eventapplication.event.dto.EventDto;
import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.event.exception.EventServiceException;
import com.example.eventapplication.event.repository.EventRepository;
import com.example.eventapplication.event.service.EventService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    public void should_create_new_event_successfully() {
        //given
        EventDto exampleDto = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");

        EventEntity referenceEntity = new EventEntity(
                exampleDto.getCity(),
                exampleDto.getEventDate(),
                exampleDto.getEventName());

        //when
        eventService.createEvent(exampleDto);

        //then
        List<EventEntity> all = eventRepository.findAll();
        assertThat(all).hasSize(1);
        EventEntity eventEntity = all.get(0);
        assertThat(eventEntity).isEqualTo(referenceEntity);
    }

    @Test
    void should_get_all_events_successfully() {
        //given
        EventDto exampleDto1 = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto1);

        EventDto exampleDto2 = new EventDto(
                "Cracow",
                "2027-05-26 13:00:00",
                "Concert");
        eventService.createEvent(exampleDto2);

        //when
        List<EventDto> events = eventService.getAllEvents();

        //then
        assertThat(events).hasSize(2);
    }

    @Test
    void should_get_all_events_by_city_successfully() {
        //given
        EventDto exampleDto1 = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto1);

        EventDto exampleDto2 = new EventDto(
                "Cracow",
                "2027-05-26 13:00:00",
                "Concert");
        eventService.createEvent(exampleDto2);

        EventDto exampleDto3 = new EventDto(
                "Warsaw",
                "2028-05-26 14:00:00",
                "Concert");
        eventService.createEvent(exampleDto3);

        //when
        List<EventDto> eventsByCity = eventService.getAllEventsByCity("Warsaw");

        //then
        assertThat(eventsByCity).hasSize(2);
    }

    @Test
    void should_not_allow_get_city_without_events() {
        //given
        EventDto exampleDto1 = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto1);

        EventDto exampleDto2 = new EventDto(
                "Cracow",
                "2027-05-26 13:00:00",
                "Concert");
        eventService.createEvent(exampleDto2);

        //when
        List<EventDto> eventsByCity = eventService.getAllEventsByCity("Katowice");

        //then
        assertThat(eventsByCity).hasSize(0);
    }

    @Test
    public void should_delete_the_existing_event_successfully() {
        //given
        EventDto exampleDto = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto);

        EventEntity eventEntity = eventRepository.findAll().get(0);

        //when
        eventService.deleteEvent(eventEntity.getTechnicalEventId());

        //then
        List<EventEntity> all = eventRepository.findAll();
        assertThat(all).hasSize(0);
    }

    @Test
    void should_throw_an_exception_when_deleting_a_non_existent_event() {
        //given
        EventDto exampleDto = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto);

        //when
        Executable e = () -> eventService.deleteEvent(UUID.randomUUID());

        //then
        EventServiceException eventServiceException = assertThrows(
                EventServiceException.class, e);
        assertThat(eventServiceException.getMessage()).contains(
                "The event was not deleted because it does not exist!");
    }

    @Test
    void should_not_throw_an_exception_when_deleting_an_existing_event() {
        //given
        EventDto exampleDto = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto);

        EventEntity eventEntity = eventRepository.findAll().get(0);

        //when
        Executable e = () -> eventService.deleteEvent(eventEntity.getTechnicalEventId());

        //then
        assertDoesNotThrow(e);
    }

    @Test
    public void should_update_the_existing_event_successfully() {
        //given
        EventDto exampleDto1 = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto1);

        EventEntity eventEntity = eventRepository.findAll().get(0);

        EventDto exampleDto2 = new EventDto(
                "Warsaw",
                "2027-05-26 12:00:00",
                "Concert123");

        EventEntity exampleEntity2 = new EventEntity(
                exampleDto2.getCity(),
                exampleDto2.getEventDate(),
                exampleDto2.getEventName());

        //when
        eventService.updateEvent(eventEntity.getTechnicalEventId(), exampleDto2);

        //then
        List<EventEntity> all = eventRepository.findAll();
        Assertions.assertThat(all.get(0)).isEqualTo(exampleEntity2);
    }

    @Test
    void should_throw_an_exception_when_updating_a_non_existent_event() {
        //given
        EventDto exampleDto1 = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto1);

        EventEntity eventEntity = eventRepository.findAll().get(0);

        EventDto exampleDto2 = new EventDto(
                "Warsaw",
                "2027-05-26 12:00:00",
                "Concert123");

        //when
        Executable e = () -> eventService.updateEvent(UUID.randomUUID(), exampleDto2);

        //then
        EventServiceException eventServiceException = assertThrows(
                EventServiceException.class, e);
        assertThat(eventServiceException.getMessage()).contains(
                "The event was not updated because it does not exist!");
    }

    @Test
    void should_not_throw_an_exception_when_updating_an_existing_event() {
        //given
        EventDto exampleDto1 = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");
        eventService.createEvent(exampleDto1);

        EventEntity eventEntity = eventRepository.findAll().get(0);

        EventDto exampleDto2 = new EventDto(
                "Warsaw",
                "2027-05-26 12:00:00",
                "Concert123");

        //when
        Executable e = () -> eventService.updateEvent(eventEntity.getTechnicalEventId(), exampleDto2);

        //then
        assertDoesNotThrow(e);
    }
}