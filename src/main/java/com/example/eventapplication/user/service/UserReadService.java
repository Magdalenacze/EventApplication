package com.example.eventapplication.user.service;

import com.example.eventapplication.user.entity.UserEntity;

import java.util.List;

public interface UserReadService {

    List<UserEntity> getUserByCity(String city);
}
