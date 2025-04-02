package com.example.weatherapp.service;
import com.example.weatherapp.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@PropertySource("classpath:application.properties")
public class WeatherService {

    @Value(value = "${api.key}")
    private String API_KEY;

    @Value(value = "${api.root}")
    private String API_ROOT;

    private final RestClient restClient;

    public WeatherService() {
        this.restClient = RestClient.create();
    }

    public WeatherDto getWeatherDetails(double lat, double lon) {

        var response = restClient.get()
                .uri(API_ROOT +  "?&key=" + API_KEY + "&q=" + lat + "," + lon + "&aqi=yes")
                .retrieve()
                .body(WeatherDto.class);

        return response;
    }
}
