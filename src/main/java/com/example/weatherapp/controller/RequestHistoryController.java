package com.example.weatherapp.controller;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.RequestHistoryServiceImpl;
import com.example.weatherapp.service.WeatherServiceImpl;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.service.interfaces.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/request-history")
public class RequestHistoryController {

    private final WeatherService weatherService;
    private final RequestHistoryService requestHistoryService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public RequestHistoryController(WeatherServiceImpl weatherServiceImpl, RequestHistoryServiceImpl requestHistoryServiceImpl, UserService userService, UserMapper userMapper, UserRepository userRepository) {
        this.weatherService = weatherServiceImpl;
        this.requestHistoryService = requestHistoryServiceImpl;
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addRequestHistory(Long userId, double latitude, double longitude) {

        WeatherDto weatherDto = weatherService.getWeatherDetails(latitude, longitude);

        RequestHistory requestHistory = new RequestHistory();

        User user = userMapper.toEntity(userService.getUserById(userId));

        if(user == null)
        {
            return new ResponseEntity<>( "User not found", HttpStatus.NOT_FOUND);
        }

        user.setVersion(0L);

        requestHistory.setUser(user);
        requestHistory.setLat(latitude);
        requestHistory.setLon(longitude);
        requestHistory.setResponse(weatherDto.toString());

        user.addRequest(requestHistory);

        requestHistoryService.addRequestHistory(requestHistory);
        return new ResponseEntity<>( "Request history added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Collection<RequestHistoryDto>> getRequestHistory(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Collection<RequestHistoryDto> requestHistories = userDto.getRequestHistories();
        return new ResponseEntity<>(requestHistories, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<WeatherDto> getDetails(@RequestParam double lat, @RequestParam double lon) {
        WeatherDto response = weatherService.getWeatherDetails(lat, lon);
        return ResponseEntity.ok(response);
    }

}
