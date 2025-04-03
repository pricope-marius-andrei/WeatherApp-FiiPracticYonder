package com.example.weatherapp.dto;

import com.example.weatherapp.dao.UserProfile;
import jakarta.persistence.Embedded;

public class UserDto {

    private Long id;
    private String name;
    private String username;

    @Embedded
    private UserProfileDto userProfile;

    public UserDto(Long id, String name, String username, UserProfileDto userProfile) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.userProfile = userProfile;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserProfileDto getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDto userProfile) {
        this.userProfile = userProfile;
    }
}
