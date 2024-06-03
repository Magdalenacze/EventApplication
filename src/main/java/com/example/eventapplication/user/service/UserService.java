package com.example.eventapplication.user.service;

import com.example.eventapplication.user.dto.UserDto;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);

    List<UserDto> getAllNotificationsForUser();
}
