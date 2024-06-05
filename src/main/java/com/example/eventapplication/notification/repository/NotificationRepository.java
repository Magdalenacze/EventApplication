package com.example.eventapplication.notification.repository;

import com.example.eventapplication.notification.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    Optional<NotificationEntity> findByTechnicalUserIdAndTechnicalEventId(UUID technicalEventId, UUID technicalUserId);
}
