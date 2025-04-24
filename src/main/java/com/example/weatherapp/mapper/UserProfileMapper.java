package com.example.weatherapp.mapper;

import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.dto.UserProfileDto;

public interface UserProfileMapper {
    UserProfileDto toDto(UserProfileModel userProfileModel);
    UserProfileModel toEntity(UserProfileDto userProfileDto);
}
