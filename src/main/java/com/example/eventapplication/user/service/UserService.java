package com.example.eventapplication.user.service;

import com.example.eventapplication.notification.dto.NotificationDto;
import com.example.eventapplication.user.dto.UserDto;
import com.example.eventapplication.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    void createUser(UserDto userDto);

    List<NotificationDto> getAllNotificationsForUser(UUID technicalUserId);
}
