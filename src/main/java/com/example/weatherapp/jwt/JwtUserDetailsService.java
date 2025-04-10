package com.example.weatherapp.jwt;

import com.example.weatherapp.model.UserModel;
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

       System.out.println("Loading user by username: " + username);
       UserModel user = userModelRepository.findByUsername(username);

       System.out.println("User found: " + user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public void saveUser(UserModel userModel) throws IllegalArgumentException {

        UserModel existingUser = userModelRepository.findByUsername(userModel.getUsername());

        if(existingUser != null)
        {
            throw new IllegalArgumentException("User with this username already exists!");
        }

        userModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
        userModelRepository.save(userModel);
    }
}
