package com.example.weatherapp.mapper;

import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;

public interface UserMapper {
    UserDto toDto(UserModel userModel);
    UserModel toEntity(UserDto userDto);
}
