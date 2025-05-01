package com.example.weatherapp.service;

import com.example.weatherapp.exception.PasswordIsNullException;
import com.example.weatherapp.exception.UsernameAlreadyExistsException;
import com.example.weatherapp.exception.UsernameIsNullException;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.model.UserProfileModel;
import com.example.weatherapp.repository.UserModelRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserModelRepository userModelRepository;

    public JwtUserDetailsService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       UserModel user = userModelRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public void saveUser(UserModel userModel) throws IllegalArgumentException {

        if(userModel.getUsername() == null || userModel.getUsername().isEmpty())
        {
            throw new UsernameIsNullException();
        }

        if(userModel.getPassword() == null || userModel.getPassword().isEmpty())
        {
            throw new PasswordIsNullException();
        }

        UserModel existingUser = userModelRepository.findByUsername(userModel.getUsername());

        if(existingUser != null)
        {
            throw new UsernameAlreadyExistsException(userModel.getUsername());
        }

        if(userModel.getUserProfile() == null)
        {
            UserProfileModel userProfileModel = new UserProfileModel();
            userModel.setUserProfile(userProfileModel);
        }

        userModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
        userModelRepository.save(userModel);
    }
}
