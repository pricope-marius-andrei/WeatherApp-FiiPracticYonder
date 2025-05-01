package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.service.interfaces.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        List<UserDto> users = userService.getUsers(pageable).getContent();

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
        response.put("message", "User deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {

        userDto.setId(id);

        UserDto existingUser = userService.getUserById(id);

        if (existingUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDto updatedUser = userService.updateUser(id, userDto);

        if (updatedUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
