package com.example.weatherapp.controller;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        User user = new User();

        List<String> names = List.of(new String[]{"John Doe", "Jane Doe", "John Smith", "Jane Smith"});
        String name = names.get(new Random().nextInt(names.size()));

        List<String> passwords = List.of(new String[]{"password", "password1", "password2", "password3"});
        String password = BCrypt.hashpw(passwords.get(new Random().nextInt(passwords.size())), BCrypt.gensalt());

        user.setName(name);

        user.setPassword(password);
        user.setUsername(name.toLowerCase(Locale.ROOT).replace(" ", "."));

        user.setUserProfile(null);
        user.setRequestHistories(null);

        userService.saveUser(user);
    }

}
