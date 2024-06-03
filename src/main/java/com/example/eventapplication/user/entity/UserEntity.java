package com.example.eventapplication.user.entity;

import com.example.eventapplication.notification.entity.NotificationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private UUID technicalUserId;
    private String userName;
    private String city;
    private String email;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<NotificationEntity> notificationEntityListForUser;

    public UserEntity(String userName, String city, String email) {
        this.technicalUserId = UUID.randomUUID();
        this.userName = userName;
        this.city = city;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(city, that.city) &&
                Objects.equals(email, that.email) &&
                Objects.equals(notificationEntityListForUser, that.notificationEntityListForUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, city, email, notificationEntityListForUser);
    }

    public void completeTheNotificationListForUser(NotificationEntity notificationEntity) {
        this.notificationEntityListForUser.add(notificationEntity);
    }
}