package com.example.weatherapp.dto;

import java.util.Set;

public class UserDto {

    private Long id;
    private String name;
    private String username;

    private UserProfileDto userProfile;

    private Set<RequestHistoryDto> requestHistories;

    public UserDto(Long id, String name, String username, UserProfileDto userProfile, Set<RequestHistoryDto> requestHistories) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.userProfile = userProfile;
        this.requestHistories = requestHistories;
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

    public Set<RequestHistoryDto> getRequestHistories() {
        return requestHistories;
    }

    public void setRequestHistories(Set<RequestHistoryDto> requestHistories) {
        if(this.requestHistories != null) {
            this.requestHistories.addAll(requestHistories);
        }
        else
        {
            this.requestHistories = requestHistories;
        }
    }

}
