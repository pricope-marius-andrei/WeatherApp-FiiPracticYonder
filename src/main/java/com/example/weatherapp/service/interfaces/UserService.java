package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<UserDto> getUsers();
    UserDto getUserById(Long id);
    UserDto getUserByProfileId(Long profileId);
    void deleteUserById(Long id);
}
