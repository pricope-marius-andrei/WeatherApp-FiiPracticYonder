package com.example.weatherapp.service;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(User user) {
        System.out.println("user: " + user);

       return userRepository.save(user);
    }

    public List<UserDto> getUsers() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> new UserDto(user.getId(),user.getName(),user.getUsername())).toList();
    }
}
