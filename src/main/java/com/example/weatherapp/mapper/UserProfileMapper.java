package com.example.weatherapp.mapper;

import com.example.weatherapp.model.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;

public interface UserProfileMapper {
    UserProfileDto toDto(UserProfile userProfile);
    UserProfile toEntity(UserProfileDto userProfileDto);
}
