package com.example.eventapplication.event.controller;

import com.example.eventapplication.event.dto.EventDto;
import com.example.eventapplication.event.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAllEvents(@RequestParam(required = false) EventDto eventDto) {
        return eventService.getAllEvents();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAllEventsByCity(@RequestParam String city) {
        return eventService.getAllEventsByCity(city);
    }

    @DeleteMapping("{technicalEventId}")
    public void deleteEvent(@PathVariable("technicalEventId") UUID technicalEventId) {
        eventService.deleteEvent(technicalEventId);
    }

    @PatchMapping("{technicalEventId}")
    public void updateEvent(@PathVariable("technicalEventId") UUID technicalEventId, @RequestBody EventDto eventDto) {
        eventService.updateEvent(technicalEventId, eventDto);
    }
}