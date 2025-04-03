package com.example.weatherapp.mapper;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
