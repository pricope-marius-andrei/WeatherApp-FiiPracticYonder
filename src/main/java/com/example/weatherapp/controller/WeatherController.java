package com.example.weatherapp.controller;

import com.example.weatherapp.controller.annotation.LogRequestHistory;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.service.WeatherServiceImpl;
import com.example.weatherapp.service.interfaces.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherServiceImpl weatherServiceImpl) {
        this.weatherService = weatherServiceImpl;
    }

    @LogRequestHistory
    @GetMapping("/details")
    public ResponseEntity<WeatherDto> getDetails(@RequestParam double lat, @RequestParam double lon) {
        WeatherDto response = weatherService.getWeatherDetails(lat, lon);
        return ResponseEntity.ok(response);
    }
}
