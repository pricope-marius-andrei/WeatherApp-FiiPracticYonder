package com.example.weatherapp.controller;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostConstruct
//    public void init() {
//        User user = new User();
//
//        List<String> names = List.of(new String[]{"John Doe", "Jane Doe", "John Smith", "Jane Smith"});
//        String name = names.get(new Random().nextInt(names.size()));
//
//        List<String> passwords = List.of(new String[]{"password", "password1", "password2", "password3"});
//        String password = BCrypt.hashpw(passwords.get(new Random().nextInt(passwords.size())), BCrypt.gensalt());
//
//        user.setName(name);
//
//        user.setPassword(password);
//        user.setUsername(name.toLowerCase(Locale.ROOT).replace(" ", "."));
//
//        user.setUserProfile(null);
//        user.setRequestHistories(null);
//
//        userService.saveUser(user);
//    }

    @GetMapping
    public List<UserDto> getAllUsers() {

        List<UserDto> users = userService.getUsers();

        return users;
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {

        System.out.println("User: " + user);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        userService.saveUser(user);
    }
}
