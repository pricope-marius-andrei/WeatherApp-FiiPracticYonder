package com.example.weatherapp.service;
import com.example.weatherapp.controller.annotation.LogRequestHistory;
import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.service.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@PropertySource("classpath:application.properties")
public class WeatherServiceImpl implements WeatherService {

    @Value(value = "${api.key}")
    private String API_KEY;

    @Value(value = "${api.root}")
    private String API_ROOT;

    private final RestClient restClient;

    public WeatherServiceImpl() {
        this.restClient = RestClient.create();
    }

    @Override
    @LogRequestHistory
    public WeatherDto getWeatherDetails(double lat, double lon) {

        return restClient.get()
                .uri(API_ROOT +  "?&key=" + API_KEY + "&q=" + lat + "," + lon + "&aqi=yes")
                .retrieve()
                .body(WeatherDto.class);
    }

    @Override
    @LogRequestHistory
    public WeatherDto getWeatherDetailsByLocation(String location) {
        return restClient.get()
                .uri(API_ROOT + "?&key=" + API_KEY + "&q=" + location + "&aqi=yes")
                .retrieve()
                .body(WeatherDto.class);
    }
}
