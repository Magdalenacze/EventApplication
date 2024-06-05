package com.example.eventapplication.notification.service;

import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.notification.entity.NotificationEntity;
import com.example.eventapplication.notification.repository.NotificationRepository;
import com.example.eventapplication.user.entity.UserEntity;
import com.example.eventapplication.user.service.UserReadService;
import com.example.eventapplication.user.service.UserUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService, EventCreatedEventListener {

    private final UserReadService userReadService;
    private final NotificationRepository notificationRepository;
    private final UserUpdateService userUpdateService;

    @Override
    public void createNotification(EventEntity eventEntity) {
        List<UserEntity> userByCityList = userReadService.getUserByCity(eventEntity.getCity());
        String notificationContent = eventEntity.getEventName() + " will take place on " +
                eventEntity.getEventDate() + " in " + eventEntity.getCity() + ".";
        userByCityList
                .stream()
                .forEach(e -> notificationRepository.save(new NotificationEntity(
                        eventEntity.getTechnicalEventId(),
                        e.getTechnicalUserId(),
                        notificationContent)));
        completeTheListOfNotificationsForTheUser(eventEntity, userByCityList);
    }

    private void completeTheListOfNotificationsForTheUser(EventEntity eventEntity, List<UserEntity> userByCityList) {
        userByCityList
                .stream()
                .forEach(e -> userUpdateService.updateNotificationsForUser(
                        notificationRepository.findByTechnicalUserIdAndTechnicalEventId(
                                e.getTechnicalUserId(), eventEntity.getTechnicalEventId()).get(), e));
    }

    @Override
    public void notifyEventCreated(EventEntity eventEntity) {
        createNotification(eventEntity);
    }
}