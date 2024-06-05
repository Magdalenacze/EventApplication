package com.example.eventapplication.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
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
    private String notificationContent;

    public NotificationEntity(UUID technicalEventId, UUID technicalUserId, String notificationContent) {
        this.technicalNotificationId = UUID.randomUUID();
        this.technicalEventId = technicalEventId;
        this.technicalUserId = technicalUserId;
        this.notificationContent = notificationContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationEntity that)) return false;
        return Objects.equals(notificationContent, that.notificationContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationContent);
    }
}