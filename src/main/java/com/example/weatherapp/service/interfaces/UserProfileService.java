package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.dto.UserProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserProfileService {
    Page<UserProfileDto> getUserProfiles(Pageable pageable);
    UserProfileDto getUserProfileById(Long id);
    UserProfileDto saveUserProfile(UserProfileModel userProfileModel);
    void updateUserProfile(Long id, UserProfileDto userProfileModel);
    void deleteUserProfile(Long id);
}
