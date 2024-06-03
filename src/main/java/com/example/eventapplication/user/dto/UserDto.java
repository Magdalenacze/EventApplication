package com.example.eventapplication.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class UserDto {

    private String userName;
    private String city;
    private String email;
}
