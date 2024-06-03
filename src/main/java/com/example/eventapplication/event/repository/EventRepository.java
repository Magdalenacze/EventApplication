package com.example.eventapplication.event.repository;

import com.example.eventapplication.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    List<EventEntity> findAllByCityIgnoreCase(String city);

    Optional<EventEntity> findByTechnicalEventId(UUID technicalEventId);

    Optional<EventEntity> deleteByTechnicalEventId(UUID technicalEventId);
}