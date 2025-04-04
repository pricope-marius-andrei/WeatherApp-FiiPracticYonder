package com.example.weatherapp.service;

import com.example.weatherapp.convert.UserDtoConverter;
import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.utils.CustomBeanUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    public final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserDtoConverter userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public void saveUser(User user) {
        System.out.println("user: " + user);

        userRepository.save(user);
    }

    public List<UserDto> getUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByProfileId(Long profileId) {

        UserDto user = userMapper.toDto(userRepository.findByProfileId(profileId));

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return;
        }

        CustomBeanUtils.copyNonNullProperties(user, existingUser);

        System.out.println("existingUser: " + existingUser);

        userMapper.toDto(userRepository.save(existingUser));
    }

}
