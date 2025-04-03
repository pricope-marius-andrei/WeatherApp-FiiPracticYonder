package com.example.weatherapp.controller;

import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.service.UserProfileServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserProfileServiceImpl userProfileServiceImpl;

    public UserProfileController(UserProfileServiceImpl userProfileServiceImpl) {
        this.userProfileServiceImpl = userProfileServiceImpl;
    }

    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
        return userProfileServiceImpl.getUserProfiles();
    }

    @GetMapping("/{id}")
    public UserProfile getUserProfileById(@PathVariable Long id) {
        return userProfileServiceImpl.getUserProfileById(id);
    }

//    @GetMapping("/by-user/{userId}")
//    public UserProfile getUserProfileByUserId(@PathVariable Long userId) {
//        return userProfileService.getUserProfileByUserId(userId);
//    }

    @PostMapping
    public UserProfile saveUserProfile(@RequestBody UserProfile userProfile) {
        System.out.println("userProfile: " + userProfile);

        return userProfileServiceImpl.saveUserProfile(userProfile);
    }

    @PutMapping("/{id}")
    public UserProfile updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        return userProfileServiceImpl.updateUserProfile(id, userProfile);
    }

    @DeleteMapping("/{id}")
    public void deleteUserProfile(@PathVariable Long id) {
        userProfileServiceImpl.deleteUserProfile(id);
    }
}
