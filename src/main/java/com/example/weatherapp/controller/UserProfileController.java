package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.model.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.service.UserProfileServiceImpl;
import com.example.weatherapp.service.interfaces.UserProfileService;
import com.example.weatherapp.service.interfaces.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserProfileService userProfileServiceImpl;
    private final UserService userService;

    public UserProfileController(UserProfileServiceImpl userProfileServiceImpl, UserService userService) {
        this.userProfileServiceImpl = userProfileServiceImpl;
        this.userService = userService;
    }

    @GetMapping
    public List<UserProfileDto> getAllUserProfiles() {
        return userProfileServiceImpl.getUserProfiles();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "userProfiles", key = "#id")
    public UserProfileDto getUserProfileById(@PathVariable Long id) {
        return userProfileServiceImpl.getUserProfileById(id);
    }

    @PostMapping
    public ResponseEntity<Object> saveUserProfile(@RequestBody UserProfile userProfile) {

        UserProfileDto userProfileDto = userProfileServiceImpl.saveUserProfile(userProfile);

        if (userProfileDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> response = Map.of(
                "id", userProfileDto.getId(),
                "message", "User profile created successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        userProfileServiceImpl.updateUserProfile(id, userProfile);

        Map<String, Object> response = Map.of(
                "id", id,
                "message", "User profile updated successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUserProfile(@AuthenticationPrincipal UserDetails userDetails) {

        UserDto user = userService.getUserByUsername(userDetails.getUsername());

        userProfileServiceImpl.deleteUserProfile(user.getId());

        Map<String, String> response = Map.of(
                "message", "User profile deleted successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
