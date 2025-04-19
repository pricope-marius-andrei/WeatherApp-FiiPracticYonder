package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.model.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserProfileService {
    Page<UserProfileDto> getUserProfiles(Pageable pageable);
    UserProfileDto getUserProfileById(Long id);
    UserProfileDto saveUserProfile(UserProfile userProfile);
    void updateUserProfile(Long id, UserProfile userProfile);
    void deleteUserProfile(Long id);
}
