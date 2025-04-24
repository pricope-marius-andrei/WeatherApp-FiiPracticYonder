package com.example.weatherapp.convert;

import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.mapper.UserProfileMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDtoConverter implements UserProfileMapper {

    private final ModelMapper modelMapper;

    public UserProfileDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserProfileDto toDto(UserProfileModel userProfileModel) {
        return modelMapper.map(userProfileModel, UserProfileDto.class);
    }

    @Override
    public UserProfileModel toEntity(UserProfileDto userProfileDto) {
        return modelMapper.map(userProfileDto, UserProfileModel.class);
    }
}
