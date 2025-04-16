package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.dto.WeatherDto;

public interface WeatherService {

    WeatherDto getWeatherDetails(double lat, double lon);

    WeatherDto getWeatherDetailsByLocation(String location);
}
