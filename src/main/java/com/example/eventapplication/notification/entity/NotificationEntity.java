package com.example.eventapplication.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    private UUID technicalNotificationId;
    private UUID technicalEventId;
    private UUID technicalUserId;

    public NotificationEntity(UUID technicalEventId, UUID technicalUserId) {
        this.technicalNotificationId = UUID.randomUUID();
        this.technicalEventId = technicalEventId;
        this.technicalUserId = technicalUserId;
    }
}