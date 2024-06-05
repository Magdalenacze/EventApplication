package com.example.eventapplication.user;

import com.example.eventapplication.notification.entity.NotificationEntity;
import com.example.eventapplication.user.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserEntityTest {

    @Test
    void should_complete_the_notification_list_for_user_successfully() {
        //given
        UserEntity exampleUser = new UserEntity(
                "Jason",
                "Warsaw",
                "jason@gmail.com");

        NotificationEntity exampleNotification = new NotificationEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Test");

        //when
        exampleUser.completeTheNotificationListForUser(exampleNotification);

        //then
        Assertions.assertThat(exampleUser.getNotificationEntityListForUser().get(0)).isEqualTo(exampleNotification);
    }
}