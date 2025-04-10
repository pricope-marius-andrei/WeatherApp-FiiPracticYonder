package com.example.weatherapp.service;

import com.example.weatherapp.convert.UserDtoConverter;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.repository.UserModelRepository;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.utils.CustomBeanUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UserModelRepository userModelRepository;
    public final UserMapper userMapper;

    public UserServiceImpl(UserModelRepository userModelRepository, UserDtoConverter userMapper) {
        this.userModelRepository = userModelRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public void saveUser(UserModel userModel) {
        System.out.println("user: " + userModel);

        userModelRepository.save(userModel);
    }

    public List<UserDto> getUsers() {

        List<UserModel> userModels = userModelRepository.findAll();

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
    public UserDto getUserByProfileId(Long profileId) {

        UserDto user = userMapper.toDto(userModelRepository.findByProfileId(profileId));

        return user;
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

        System.out.println("existingUser: " + existingUserModel);

        userMapper.toDto(userModelRepository.save(existingUserModel));
    }

}
