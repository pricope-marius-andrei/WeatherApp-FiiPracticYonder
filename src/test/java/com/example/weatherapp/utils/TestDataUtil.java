package com.example.weatherapp.utils;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.UserProfileDto;

import java.util.List;

public class TestDataUtil {

    public static List<UserDto> createTestUsers() {

        return List.of(
                new UserDto(1L, "User1", "username1", new UserProfileDto(1L, "username1@gmail.com", false), null),
                new UserDto(2L, "User2", "username2", null, null),
                new UserDto(3L, "User3", "username3", null, null),
                new UserDto(4L, "User4", "username4", null, null)
        );
    }

    public static List<UserProfileDto> createTestUserProfiles() {
        return List.of(
                new UserProfileDto(1L, "username1@gmail.com", false),
                new UserProfileDto(2L, "username2@gmail.com", false),
                new UserProfileDto(3L, "username3@gmail.com", true),
                new UserProfileDto(4L, "username4@gmail.com", false)
        );
    }
}
