package com.example.weatherapp.controller;

import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.service.UserServiceImpl;
import com.example.weatherapp.service.interfaces.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);

        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> getUserByProfileId(@PathVariable Long id) {
        UserDto user = userService.getUserByProfileId(id);

        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {

        UserDto user = userService.getUserById(id);
        if(user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User was deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    // Problem: When I don't pass the user_profile id in the body, it creates a new user_profile
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {

        if(!userModel.getId().equals(id))
            return new ResponseEntity<>("User ID in the path and body do not match!", HttpStatus.BAD_REQUEST);

        UserDto existingUser = userService.getUserById(id);

        if (existingUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        userModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
        userService.updateUser(id, userModel);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User was updated successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
