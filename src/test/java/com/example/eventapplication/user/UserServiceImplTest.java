package com.example.eventapplication.user;

import com.example.eventapplication.notification.dto.NotificationDto;
import com.example.eventapplication.notification.entity.NotificationEntity;
import com.example.eventapplication.user.dto.UserDto;
import com.example.eventapplication.user.entity.UserEntity;
import com.example.eventapplication.user.exception.UserServiceException;
import com.example.eventapplication.user.repository.UserRepository;
import com.example.eventapplication.user.service.UserReadService;
import com.example.eventapplication.user.service.UserService;
import com.example.eventapplication.user.service.UserUpdateService;
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
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserUpdateService userUpdateService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void should_create_new_user_successfully() {
        //given
        UserDto exampleDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");

        UserEntity referenceEntity = new UserEntity(
                exampleDto.getUserName(),
                exampleDto.getCity(),
                exampleDto.getEmail());

        //when
        userService.createUser(exampleDto);

        //then
        List<UserEntity> all = userRepository.findAll();
        assertThat(all).hasSize(1);
        UserEntity userEntity = all.get(0);
        assertThat(userEntity).isEqualTo(referenceEntity);
    }

    @Test
    void should_get_all_notifications_for_user_successfully() {
        //given
        UserDto exampleDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");
       userService.createUser(exampleDto);
       List<UserEntity> users = userRepository.findAll();

        //when
        List<NotificationDto> notifications = userService.getAllNotificationsForUser(users.get(0).getTechnicalUserId());

        //then
        assertThat(notifications).hasSize(0);
    }

    @Test
    void should_throw_an_exception_when_displaying_a_list_of_notifications_for_a_non_existent_user() {
        //given
        UserDto exampleDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");
       userService.createUser(exampleDto);

        //when
        Executable e = () -> userService.getAllNotificationsForUser(UUID.randomUUID());

        //then
        UserServiceException userServiceException = assertThrows(
                UserServiceException.class, e);
        assertThat(userServiceException.getMessage()).contains(
                "Notifications cannot be displayed because the specified user does not exist!");
    }
//
    @Test
    void should_not_throw_an_exception_when_deleting_an_existing_event() {
        //given
        UserDto exampleDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");
        userService.createUser(exampleDto);

        UserEntity userEntity = userRepository.findAll().get(0);

        //when
        Executable e = () -> userService.getAllNotificationsForUser(userEntity.getTechnicalUserId());

        //then
        assertDoesNotThrow(e);
    }

    @Test
    public void should_get_user_by_city_successfully() {
        //given
        UserDto exampleDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");
        userService.createUser(exampleDto);

        UserEntity referenceEntity = new UserEntity(
                exampleDto.getUserName(),
                exampleDto.getCity(),
                exampleDto.getEmail());

        //when
        userReadService.getUserByCity("Warsaw");

        //then
        List<UserEntity> all = userRepository.findAll();
        Assertions.assertThat(all.get(0)).isEqualTo(referenceEntity);
    }

    @Test
    public void should_update_notifications_for_user_successfully() {
        //given
        UserDto userDto = new UserDto(
                "Jason",
                "Warsaw",
                "jason@gmail.com");
        userService.createUser(userDto);

        UserEntity userEntity = new UserEntity(
                userDto.getUserName(),
                userDto.getCity(),
                userDto.getEmail());

        NotificationEntity notificationEntity = new NotificationEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Test");

        //when
        userUpdateService.updateNotificationsForUser(notificationEntity, userEntity);

        //then
        Assertions.assertThat(userEntity.getNotificationEntityListForUser().get(0)).isEqualTo(notificationEntity);
    }
}