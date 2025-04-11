package com.example.weatherapp.service;

import com.example.weatherapp.convert.UserDtoConverter;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.repository.UserModelRepository;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.utils.CustomBeanUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UserModelRepository userModelRepository;
    public final UserMapper userMapper;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserModelRepository userModelRepository, UserDtoConverter userMapper) {
        this.userModelRepository = userModelRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public void saveUser(UserModel userModel) {
        userModelRepository.save(userModel);
    }

    public List<UserDto> getUsers() {

        log.info("Fetching users data...");
        List<UserModel> userModels = userModelRepository.findAll();

        if (userModels.isEmpty()) {
            log.info("No users found.");
            return List.of();
        }
        return userModels.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        UserModel userModel = userModelRepository.findById(id).orElse(null);
        if (userModel == null) {
            return null;
        }

        return userMapper.toDto(userModel);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserModel userModel = userModelRepository.findByUsername(username);
        if (userModel == null) {
            return null;
        }

        return userMapper.toDto(userModel);
    }

    @Override
    public UserDto getUserByProfileId(Long profileId) {
        return userMapper.toDto(userModelRepository.findByProfileId(profileId));
    }

    @Override
    public void deleteUserById(Long id) {
        userModelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserModel userModel) {

        UserModel existingUserModel = userModelRepository.findById(id).orElse(null);

        if (existingUserModel == null) {
            return;
        }

        CustomBeanUtils.copyNonNullProperties(userModel, existingUserModel);
        userMapper.toDto(userModelRepository.save(existingUserModel));
    }

}
