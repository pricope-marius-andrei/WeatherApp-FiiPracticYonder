package com.example.weatherapp.mapper;

import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;

public interface UserProfileMapper {
    UserProfileDto toDto(UserProfile userProfile);
    UserProfile toEntity(UserProfileDto userProfileDto);
}
