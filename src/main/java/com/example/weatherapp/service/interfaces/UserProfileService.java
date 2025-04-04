package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> getUserProfiles();
    UserProfileDto getUserProfileById(Long id);
    UserProfileDto saveUserProfile(UserProfile userProfile);
    void updateUserProfile(Long id, UserProfile userProfile);
    void deleteUserProfile(Long id);
}
