package com.example.weatherapp.controller;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.service.UserServiceImpl;
import com.example.weatherapp.service.interfaces.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
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
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody User user) {

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User was not created successfully!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "User was created successfully!");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
