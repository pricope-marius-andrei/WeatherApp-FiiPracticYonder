package com.example.weatherapp.controller;

import com.example.weatherapp.controller.annotation.LogRequestHistory;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.model.LocationRequest;
import com.example.weatherapp.service.WeatherServiceImpl;
import com.example.weatherapp.service.interfaces.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final Logger logger = Logger.getLogger(WeatherController.class.getName());

    public WeatherController(WeatherServiceImpl weatherServiceImpl) {
        this.weatherService = weatherServiceImpl;
    }

    @GetMapping("/details")
    public ResponseEntity<WeatherDto> getDetails(@RequestParam double lat, @RequestParam double lon) {
        WeatherDto response = weatherService.getWeatherDetails(lat, lon);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/locations")
    public ResponseEntity<List<WeatherDto>> getDetailsByLocation(@RequestBody LocationRequest locationRequest) {
        if (locationRequest == null || locationRequest.getLocations() == null || locationRequest.getLocations().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<WeatherDto> responses = new ArrayList<>();

        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (String location : locationRequest.getLocations()) {
                Runnable task = new DelegatingSecurityContextRunnable(() -> {
                    logger.info("Fetching weather details for location: " + location);
                    WeatherDto response = weatherService.getWeatherDetailsByLocation(location);
                    if (response != null) {
                        responses.add(response);
                    }
                });

                executor.submit(task);
            }
        }

        return ResponseEntity.ok(responses);
    }
}