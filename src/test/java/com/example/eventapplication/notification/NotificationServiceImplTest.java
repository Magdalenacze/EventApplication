package com.example.eventapplication.notification;

import com.example.eventapplication.event.dto.EventDto;
import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.event.service.EventService;
import com.example.eventapplication.notification.dto.NotificationDto;
import com.example.eventapplication.notification.entity.NotificationEntity;
import com.example.eventapplication.notification.repository.NotificationRepository;
import com.example.eventapplication.notification.service.EventCreatedEventListener;
import com.example.eventapplication.notification.service.NotificationService;
import com.example.eventapplication.user.dto.UserDto;
import com.example.eventapplication.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NotificationServiceImplTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private NotificationRepository notificationRepository;

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
    }

    @Test
    public void should_create_new_notification_successfully() {
        //given
        UserDto userDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");
        userService.createUser(userDto);

        EventDto eventDto = new EventDto(
                "Warsaw",
                "2026-05-26 12:00:00",
                "Concert");

        //when
        eventService.createEvent(eventDto);

        //then
        List<NotificationEntity> all = notificationRepository.findAll();
        assertThat(all).hasSize(1);
    }
}