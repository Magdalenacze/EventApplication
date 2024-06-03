package com.example.eventapplication.event;

import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.event.exception.EventException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventEntityTest {

    @Test
    void should_create_new_event_for_the_current_date() {
        //given
        String city = "Warsaw";
        String eventDate = "2026-05-26 12:00:00";
        String eventName = "Concert";

        //when
        EventEntity EventEntity = new EventEntity(
                city,
                eventDate,
                eventName);

        //then
        Assertions.assertThat(EventEntity).isNotNull();
    }

    @Test
    void should_throw_an_exception_when_creating_a_new_event_for_a_backward_date() {
        //given
        String city = "Warsaw";
        String eventDate = "2023-05-26 12:00:00";
        String eventName = "Concert";

        //when
        Executable e = () -> new EventEntity(city, eventDate, eventName);

        //then
        assertThrows(EventException.class, e);
    }

    @Test
    void should_not_throw_an_exception_when_creating_a_new_event_for_the_current_date() {
        //given
        String city = "Warsaw";
        String eventDate = "2026-05-26 12:00:00";
        String eventName = "Concert";

        //when
        Executable e = () -> new EventEntity(city, eventDate, eventName);

        //then
        assertDoesNotThrow(e);
    }

    @Test
    void should_update_the_existing_entity_data_successfully() {
        //given
        EventEntity exampleEntity1 = new EventEntity(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");

        EventEntity exampleEntity2 = new EventEntity(
                "Warsaw",
                "2027-05-26 12:00:00",
                "Concert");

        //when
        exampleEntity1.updateEntityData(exampleEntity2);

        //then
        Assertions.assertThat(exampleEntity1).isEqualTo(exampleEntity2);
    }
}