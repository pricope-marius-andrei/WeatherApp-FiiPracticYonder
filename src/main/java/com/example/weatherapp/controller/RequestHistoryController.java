package com.example.weatherapp.controller;

import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.service.RequestHistoryServiceImpl;
import com.example.weatherapp.service.WeatherServiceImpl;
import com.example.weatherapp.service.interfaces.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request-history")
public class RequestHistoryController {

    private final WeatherService weatherService;
    private final RequestHistoryServiceImpl requestHistoryServiceImpl;

    public RequestHistoryController(WeatherServiceImpl weatherServiceImpl, RequestHistoryServiceImpl requestHistoryServiceImpl) {
        this.weatherService = weatherServiceImpl;
        this.requestHistoryServiceImpl = requestHistoryServiceImpl;
    }

    @PostMapping
    public String addRequestHistory(double latitude, double longitude) {

        WeatherDto weatherDto = weatherService.getWeatherDetails(latitude, longitude);



        return "Request history added successfully";
    }

    @GetMapping("/details")
    public ResponseEntity<WeatherDto> getDetails(@RequestParam double lat, @RequestParam double lon) {
        WeatherDto response = weatherService.getWeatherDetails(lat, lon);
        return ResponseEntity.ok(response);
    }

}
