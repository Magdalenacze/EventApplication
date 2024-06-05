package com.example.eventapplication.user.repository;

import com.example.eventapplication.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findAllUserByCity(String city);

    Optional<UserEntity> findUserByTechnicalUserId(UUID technicalUserId);
}