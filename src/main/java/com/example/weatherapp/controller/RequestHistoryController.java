package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserDto;
import com.example.weatherapp.model.RequestHistory;
import com.example.weatherapp.model.UserModel;
import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.mapper.UserMapper;
import com.example.weatherapp.service.RequestHistoryServiceImpl;
import com.example.weatherapp.service.WeatherServiceImpl;
import com.example.weatherapp.service.interfaces.EmailService;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import com.example.weatherapp.service.interfaces.UserService;
import com.example.weatherapp.service.interfaces.WeatherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request-history")
public class RequestHistoryController {

    private final WeatherService weatherService;
    private final RequestHistoryService requestHistoryService;
    private final EmailService emailService;
    private final UserService userService;
    private final UserMapper userMapper;

    public RequestHistoryController(WeatherServiceImpl weatherServiceImpl, RequestHistoryServiceImpl requestHistoryServiceImpl, EmailService emailService, UserService userService, UserMapper userMapper) {
        this.weatherService = weatherServiceImpl;
        this.requestHistoryService = requestHistoryServiceImpl;
        this.emailService = emailService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<String> addRequestHistory(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Double latitude, @RequestParam Double longitude) {

        WeatherDto weatherDto = weatherService.getWeatherDetails(latitude, longitude);

        RequestHistory requestHistory = new RequestHistory();

        UserModel userModel = userMapper.toEntity(userService.getUserByUsername(userDetails.getUsername()));

        if(userModel == null)
        {
            return new ResponseEntity<>( "User not found", HttpStatus.NOT_FOUND);
        }

        emailService.sendEmail(userModel.getUsername() + "@gmail.com", "Weather Request", weatherDto.toString());

        userModel.setVersion(0L);

        requestHistory.setUser(userModel);
        requestHistory.setLat(latitude);
        requestHistory.setLon(longitude);
        requestHistory.setResponse(weatherDto.toString());

        userModel.addRequest(requestHistory);

        requestHistoryService.addRequestHistory(requestHistory);
        return new ResponseEntity<>( "Request history added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public Page<RequestHistoryDto> getAllRequests(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        UserDto user = userService.getUserByUsername(userDetails.getUsername());

        return requestHistoryService.getPagedRequestHistoriesByUserId(user.getId(), pageable);
    }

    @GetMapping("/details")
    public ResponseEntity<WeatherDto> getDetails(@RequestParam double lat, @RequestParam double lon) {
        WeatherDto response = weatherService.getWeatherDetails(lat, lon);
        return ResponseEntity.ok(response);
    }

}
