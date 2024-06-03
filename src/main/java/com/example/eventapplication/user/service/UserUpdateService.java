package com.example.eventapplication.user.service;

import com.example.eventapplication.notification.entity.NotificationEntity;
import com.example.eventapplication.user.entity.UserEntity;

public interface UserUpdateService {

    void updateNotificationsForUser(NotificationEntity notificationEntity, UserEntity userEntity);
}
