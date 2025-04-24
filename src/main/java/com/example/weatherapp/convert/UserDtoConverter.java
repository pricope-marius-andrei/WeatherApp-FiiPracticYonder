package com.example.weatherapp.convert;

import com.example.weatherapp.model.UserModel;
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
    public UserDto toDto(UserModel userModel) {
        return modelMapper.map(userModel, UserDto.class);
    }

    @Override
    public UserModel toEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserModel.class);
    }
}
