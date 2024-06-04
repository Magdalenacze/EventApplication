package com.example.eventapplication.notification.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class NotificationDto {

    private String notificationContent;
}
