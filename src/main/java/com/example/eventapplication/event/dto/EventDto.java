package com.example.eventapplication.event.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class EventDto {

    private String city;
    private String eventDate;
    private String eventName;
}