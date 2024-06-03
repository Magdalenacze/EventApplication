package com.example.eventapplication.event.entity;

import com.example.eventapplication.event.exception.EventException;
import com.example.eventapplication.notification.entity.NotificationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    private UUID technicalEventId;
    private String city;
    private Timestamp eventDate;
    private String eventName;

//    @OneToMany
//    @JoinColumn(name = "event_id")
//    private List<NotificationEntity> notificationEntityListForEvent;

    public EventEntity(String city, String eventDate, String eventName) {
        this.eventDate = formatDate(eventDate);
        validateDate(this.eventDate);
        this.technicalEventId = UUID.randomUUID();
        this.city = city;
        this.eventName = eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventEntity that)) return false;
        return Objects.equals(city, that.city) &&
                Objects.equals(eventDate, that.eventDate) &&
                Objects.equals(eventName, that.eventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, eventDate, eventName);
    }

    private Timestamp formatDate(String eventDate) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(eventDate));
        return Timestamp.valueOf(localDateTime);
    }

    private void validateDate(Timestamp eventDate) {
        if (eventDate.before(new Date())) {
            throw new EventException("You cannot create a new backdated event!");
        }
    }

    public void updateEntityData(EventEntity updatedEventEntity) {
        this.city = updatedEventEntity.getCity();
        this.eventDate = updatedEventEntity.getEventDate();
        this.eventName = updatedEventEntity.getEventName();
    }

//    public void completeTheNotificationListForEvent(List<NotificationEntity> notificationEntities) {
//        this.notificationEntityListForEvent.addAll(notificationEntities);
//    }
}