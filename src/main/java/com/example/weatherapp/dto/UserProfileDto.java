package com.example.weatherapp.dto;

public class UserProfileDto {

    private Long id;
    private String email;
    private Boolean emailNotification;

    public UserProfileDto(Long id, String email, Boolean emailNotification, Long version) {
        this.id = id;
        this.email = email;
        this.emailNotification = emailNotification;
    }

    public UserProfileDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }
}
