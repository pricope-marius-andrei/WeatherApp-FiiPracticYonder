package com.example.weatherapp.service;

import com.example.weatherapp.convert.UserProfileDtoConverter;
import com.example.weatherapp.dao.User;
import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.mapper.UserProfileMapper;
import com.example.weatherapp.repository.UserProfileRepository;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.interfaces.UserProfileService;
import com.example.weatherapp.utils.CustomBeanUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRepository userRepository, UserProfileDtoConverter userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.userProfileMapper = userProfileMapper;
    }

    public List<UserProfileDto> getUserProfiles() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toDto)
                .toList();
    }

    public UserProfileDto getUserProfileById(Long id) {
        return userProfileMapper.toDto(userProfileRepository.findById(id).orElse(null));
    }

    @Transactional
    public UserProfileDto saveUserProfile(UserProfile userProfile) {
        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }

    public void updateUserProfile(Long id, UserProfile userProfile) {
        UserProfile exisingUserProfile = userProfileRepository.findById(id).orElse(null);

        if (exisingUserProfile == null) {
            return;
        }

        CustomBeanUtils.copyNonNullProperties(userProfile, exisingUserProfile);

        userProfileRepository.save(exisingUserProfile);
    }

    public void deleteUserProfile(Long id) {
        User user = userRepository.findByProfileId(id);

        if (user != null) {
            user.setUserProfile(null);
            userRepository.save(user);
        }

        userProfileRepository.deleteById(id);
    }
}
