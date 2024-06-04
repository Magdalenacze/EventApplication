package com.example.eventapplication.user.service;

import com.example.eventapplication.notification.dto.NotificationDto;
import com.example.eventapplication.notification.entity.NotificationEntity;
import com.example.eventapplication.user.dto.UserDto;
import com.example.eventapplication.user.entity.UserEntity;
import com.example.eventapplication.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserReadService, UserUpdateService {

    private final UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity(
                userDto.getUserName(),
                userDto.getCity(),
                userDto.getEmail());
        userRepository.save(userEntity);
    }

    @Override
    public List<NotificationDto> getAllNotificationsForUser(UUID technicalUserId) {
        return userRepository.findUserByTechnicalUserId(technicalUserId).get().getNotificationEntityListForUser()
                .stream()
                .map(e -> new NotificationDto(e.getNotificationContent()))
                .toList();
    }

    @Override
    public List<UserEntity> getUserByCity(String city) {
        return userRepository.getAllUserEntityByCity(city);
    }

    @Override
    public void updateNotificationsForUser(NotificationEntity notificationEntity, UserEntity userEntity) {
        userEntity.completeTheNotificationListForUser(notificationEntity);
    }
}