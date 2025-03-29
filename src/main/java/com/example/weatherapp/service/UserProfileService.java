package com.example.weatherapp.service;

import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.repository.UserProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getUserProfileById(Long id) {
        return userProfileRepository.findById(id).orElse(null);
    }

    public UserProfile saveUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public UserProfile updateUserProfile(Long id, UserProfile userProfile) {
        UserProfile exisingUserProfile = userProfileRepository.findById(id).orElse(null);
        if (exisingUserProfile == null) {
            return null;
        }

        BeanUtils.copyProperties(userProfile, exisingUserProfile, "id");
        return userProfileRepository.save(exisingUserProfile);
    }

    public void deleteUserProfile(Long id) {
        userProfileRepository.deleteById(id);
    }
}
