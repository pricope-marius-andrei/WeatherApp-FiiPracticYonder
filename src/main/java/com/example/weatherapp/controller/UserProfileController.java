package com.example.weatherapp.controller;

import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @GetMapping("/{id}")
    public UserProfile getUserProfileById(@PathVariable Long id) {
        return userProfileService.getUserProfileById(id);
    }

//    @GetMapping("/by-user/{userId}")
//    public UserProfile getUserProfileByUserId(@PathVariable Long userId) {
//        return userProfileService.getUserProfileByUserId(userId);
//    }

    @PostMapping
    public UserProfile saveUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.saveUserProfile(userProfile);
    }

    @PutMapping("/{id}")
    public UserProfile updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        return userProfileService.updateUserProfile(id, userProfile);
    }

    @DeleteMapping("/{id}")
    public void deleteUserProfile(@PathVariable Long id) {
        userProfileService.deleteUserProfile(id);
    }
}
