package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.service.interfaces.UserProfileService;
import com.example.weatherapp.service.interfaces.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final UserProfileService userProfileService;
    private final UserService userService;

    public UserProfileController(UserProfileService userProfileServiceImpl, UserService userService) {
        this.userProfileService = userProfileServiceImpl;
        this.userService = userService;
    }

    @GetMapping
    public List<UserProfileDto> getAllUserProfiles(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userProfileService.getUserProfiles(pageable).getContent();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "userProfiles", key = "#id")
    public UserProfileDto getUserProfileById(@PathVariable Long id) {
        return userProfileService.getUserProfileById(id);
    }

    @PostMapping
    public ResponseEntity<Object> saveUserProfile(@RequestBody UserProfileModel userProfileModel) {

        UserProfileDto userProfileDto = userProfileService.saveUserProfile(userProfileModel);

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
    public ResponseEntity<Object> updateUserProfile(@PathVariable Long id, @RequestBody UserProfileModel userProfileModel) {
        userProfileService.updateUserProfile(id, userProfileModel);

        Map<String, Object> response = Map.of(
                "id", id,
                "message", "User profile updated successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUserProfile(@AuthenticationPrincipal UserDetails userDetails) {

        UserDto user = userService.getUserByUsername(userDetails.getUsername());

        userProfileService.deleteUserProfile(user.getId());

        Map<String, String> response = Map.of(
                "message", "User profile deleted successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
