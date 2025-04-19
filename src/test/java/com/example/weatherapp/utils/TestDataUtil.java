package com.example.weatherapp.utils;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.UserProfileDto;

import java.util.List;

public class TestDataUtil {

    public static List<UserDto> createTestUsers() {

        return List.of(
                new UserDto(1L, "User1", "username1", new UserProfileDto(1L, "username1@gmail.com", false, 0L), null),
                new UserDto(2L, "User2", "username2", null, null),
                new UserDto(3L, "User3", "username3", null, null),
                new UserDto(4L, "User4", "username4", null, null)
        );

    }
}
