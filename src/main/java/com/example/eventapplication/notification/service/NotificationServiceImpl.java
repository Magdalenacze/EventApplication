package com.example.eventapplication.notification.service;

import com.example.eventapplication.event.entity.EventEntity;
import com.example.eventapplication.event.service.EventUpdateService;
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
public class NotificationServiceImpl implements NotificationService, EventCreatedInAGivenCityEventListener {

    private final UserReadService userReadService;
    private final NotificationRepository notificationRepository;
    private final UserUpdateService userUpdateService;
    //private final EventUpdateService eventUpdateService;

    @Override
    public void createNotification(EventEntity eventEntity) {
        List<UserEntity> userByCityList = userReadService.getUserByCity(eventEntity.getCity());
        userByCityList
                .stream()
                .forEach(e -> notificationRepository.save(new NotificationEntity(
                        e.getTechnicalUserId(), eventEntity.getTechnicalEventId())));
        userByCityList
                .stream()
                .forEach(e -> userUpdateService.updateNotificationsForUser(
                        notificationRepository.findByTechnicalUserIdAndTechnicalEventId(
                                e.getTechnicalUserId(),
                                eventEntity.getTechnicalEventId()).get(), e));
//        eventUpdateService.updateNotificationsForEvent(notificationRepository.
//                findAllByTechnicalEventId(eventEntity.getTechnicalEventId()), eventEntity);
    }

    @Override
    public void notifyEventCreatedInAGivenCity(EventEntity eventEntity) {
        createNotification(eventEntity);
    }
}