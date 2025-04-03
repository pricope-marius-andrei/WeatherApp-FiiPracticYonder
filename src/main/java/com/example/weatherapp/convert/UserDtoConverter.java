package com.example.weatherapp.convert;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements UserMapper {

    private final ModelMapper modelMapper;

    public UserDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
