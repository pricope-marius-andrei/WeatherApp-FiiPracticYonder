package com.example.weatherapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.weatherapp.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/details")
    public ResponseEntity<String> getDetails(@RequestParam double lat, @RequestParam double lon) {
        String response = weatherService.getWeatherDetails(lat, lon);
        return ResponseEntity.ok(response);
    }
}
