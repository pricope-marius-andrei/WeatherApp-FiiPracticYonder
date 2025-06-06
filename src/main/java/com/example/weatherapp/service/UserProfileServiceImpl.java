package com.example.weatherapp.service;

import com.example.weatherapp.convert.UserProfileDtoConverter;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.dto.UserProfileDto;
import com.example.weatherapp.mapper.UserProfileMapper;
import com.example.weatherapp.repository.UserProfileRepository;
import com.example.weatherapp.repository.UserModelRepository;
import com.example.weatherapp.service.interfaces.UserProfileService;
import com.example.weatherapp.utils.CustomBeanUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserModelRepository userModelRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserModelRepository userModelRepository, UserProfileDtoConverter userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userModelRepository = userModelRepository;
        this.userProfileMapper = userProfileMapper;
    }

    public Page<UserProfileDto> getUserProfiles(Pageable pageable) {

        Page<UserProfileModel> userProfiles = userProfileRepository.findAll(pageable);

        return userProfiles.map(userProfileMapper::toDto);
    }

    public UserProfileDto getUserProfileById(Long id) {
        return userProfileMapper.toDto(userProfileRepository.findById(id).orElse(null));
    }

    @Transactional
    public UserProfileDto saveUserProfile(UserProfileModel userProfileModel) {
        return userProfileMapper.toDto(userProfileRepository.save(userProfileModel));
    }

    public void updateUserProfile(Long id, UserProfileDto userProfile) {
        UserProfileModel exisingUserProfileModel = userProfileRepository.findById(id).orElse(null);

        if (exisingUserProfileModel == null) {
            return;
        }

        CustomBeanUtils.copyNonNullProperties(userProfile, exisingUserProfileModel);

        userProfileRepository.save(exisingUserProfileModel);
    }

    public void deleteUserProfile(Long id) {
        UserModel userModel = userModelRepository.findByProfileId(id);

        if (userModel != null) {
            userModel.setUserProfile(null);
            userModelRepository.save(userModel);
        }

        userProfileRepository.deleteById(id);
    }
}
