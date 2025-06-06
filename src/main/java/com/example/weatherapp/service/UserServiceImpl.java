package com.example.weatherapp.service;

import com.example.weatherapp.convert.UserDtoConverter;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.repository.UserModelRepository;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.utils.CustomBeanUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public final UserModelRepository userModelRepository;
    public final UserMapper userMapper;

    public UserServiceImpl(UserModelRepository userModelRepository, UserDtoConverter userMapper) {
        this.userModelRepository = userModelRepository;
        this.userMapper = userMapper;
    }

    public Page<UserDto> getUsers(Pageable pageable) {

        Page<UserModel> userModels = userModelRepository.findAll(pageable);

        if (userModels.isEmpty()) {
            return Page.empty();
        }

        return userModels.map(userMapper::toDto);
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
    public UserDto updateUser(Long id, UserDto userDto) {

        UserModel existingUserModel = userModelRepository.findById(id).orElse(null);

        if (existingUserModel == null) {
            return null;
        }

        CustomBeanUtils.copyNonNullProperties(userDto, existingUserModel);
        userMapper.toDto(userModelRepository.save(existingUserModel));

        return userMapper.toDto(existingUserModel);
    }

}
