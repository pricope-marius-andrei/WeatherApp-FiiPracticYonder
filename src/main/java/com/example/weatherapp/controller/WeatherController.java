package com.example.weatherapp.controller;

import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.exception.EmailSenderException;
import com.example.weatherapp.model.LocationRequestModel;
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
    public ResponseEntity<Object> getDetailsByLocation(@RequestBody LocationRequestModel locationRequestModel) {

        if (locationRequestModel == null || locationRequestModel.getLocations() == null || locationRequestModel.getLocations().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request: No locations provided.");
        }

        List<WeatherDto> responses = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();

        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (String location : locationRequestModel.getLocations()) {
                Runnable task = new DelegatingSecurityContextRunnable(() -> {
                    try {
                        WeatherDto response = weatherService.getWeatherDetailsByLocation(location);
                        if (response != null) {
                            responses.add(response);
                        }
                    }
                    catch (EmailSenderException e) {
                        logger.severe("Error fetching weather details for location: " + location + " - " + e.getMessage());
                        synchronized (exceptions) {
                            exceptions.add(e);
                        }
                    }
                });

                executor.submit(task);
            }

        }

        for(Exception e : exceptions) {
            return ResponseEntity.status(500).body("Error fetching weather details: " + e.getMessage());
        }

        return ResponseEntity.ok(responses);
    }
}