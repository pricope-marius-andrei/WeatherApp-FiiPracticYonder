package com.example.weatherapp.dto;

public class UserDto {

    private Long id;
    private String name;
    private String username;

    public UserDto(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
