package com.example.eventapplication.user.controller;

import com.example.eventapplication.notification.dto.NotificationDto;
import com.example.eventapplication.user.dto.UserDto;
import com.example.eventapplication.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @GetMapping("{technicalUserId}/notifications")
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationDto> getAllNotificationsForUser(@PathVariable("technicalUserId") UUID technicalUserId) {
        return userService.getAllNotificationsForUser(technicalUserId);
    }
}