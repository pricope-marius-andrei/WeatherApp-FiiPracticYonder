package com.example.weatherapp.service;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(User user) {
        System.out.println("user: " + user);

        userRepository.save(user);
    }

    public List<UserDto> getUsers() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> new UserDto(user.getId(),user.getName(),user.getUsername())).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserDto getUserByProfileId(Long profileId) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }
}
