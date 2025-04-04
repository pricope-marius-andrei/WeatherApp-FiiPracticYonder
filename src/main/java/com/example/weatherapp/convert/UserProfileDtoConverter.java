package com.example.weatherapp.convert;

import com.example.weatherapp.dao.UserProfile;
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
    public UserProfileDto toDto(UserProfile userProfile) {
        return modelMapper.map(userProfile, UserProfileDto.class);
    }

    @Override
    public UserProfile toEntity(UserProfileDto userProfileDto) {
        return modelMapper.map(userProfileDto, UserProfile.class);
    }
}
