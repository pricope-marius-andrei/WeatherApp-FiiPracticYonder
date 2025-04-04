package com.example.weatherapp.controller;

import com.example.weatherapp.dao.UserProfile;
import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.service.UserProfileServiceImpl;
import com.example.weatherapp.service.interfaces.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserProfileService userProfileServiceImpl;

    public UserProfileController(UserProfileServiceImpl userProfileServiceImpl) {
        this.userProfileServiceImpl = userProfileServiceImpl;
    }

    @GetMapping
    public List<UserProfileDto> getAllUserProfiles() {
        return userProfileServiceImpl.getUserProfiles();
    }

    @GetMapping("/{id}")
    public UserProfileDto getUserProfileById(@PathVariable Long id) {
        return userProfileServiceImpl.getUserProfileById(id);
    }

    @PostMapping
    public ResponseEntity<Object> saveUserProfile(@RequestBody UserProfile userProfile) {

        UserProfileDto userProfileDto =  userProfileServiceImpl.saveUserProfile(userProfile);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserProfile(@PathVariable Long id) {
        userProfileServiceImpl.deleteUserProfile(id);

        Map<String, String> response = Map.of(
                "message", "User profile deleted successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
