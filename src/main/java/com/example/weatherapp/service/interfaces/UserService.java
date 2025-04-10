package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserModel userModel);
    List<UserDto> getUsers();
    UserDto getUserById(Long id);
    UserDto getUserByProfileId(Long profileId);
    UserDto getUserByUsername(String username);
    void deleteUserById(Long id);
    void updateUser(Long id, UserModel userModelDto);
}
