package com.example.weatherapp.utils;

import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.UserProfileDto;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TestDataUtil {

    public static List<UserDto> createTestUsers() {


        return List.of(
                new UserDto(1L, "User1", "username1", new UserProfileDto(1L, "username1@gmail.com", false), createTestRequestHistories()),
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

    private static Set<RequestHistoryDto> createTestRequestHistories() {
        return Set.of(
               new RequestHistoryDto(1L, 2, 12, "response1", false, true, 12, false),
               new RequestHistoryDto(2L, 3, 13, "response2", false, true, 13, false),
               new RequestHistoryDto(3L, 4, 14, "response3", false, true, 14, false),
               new RequestHistoryDto(4L, 5, 15, "response4", false, true, 15, false)
        );
    }
}
