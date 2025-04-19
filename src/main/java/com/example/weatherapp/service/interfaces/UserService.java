package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> getUsers(Pageable pageable);
    UserDto getUserById(Long id);
    UserDto getUserByProfileId(Long profileId);
    UserDto getUserByUsername(String username);
    void deleteUserById(Long id);
    void updateUser(Long id, UserModel userModelDto);
}
