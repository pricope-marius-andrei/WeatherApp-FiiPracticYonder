package com.example.weatherapp.service;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.repository.RequestHistoryRepository;
import com.example.weatherapp.service.interfaces.WeatherService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RequestHistoryServiceImpl {

    private final RequestHistoryRepository requestHistoryRepository;
    private final WeatherService weatherService;

    public RequestHistoryServiceImpl(RequestHistoryRepository requestHistoryRepository, WeatherService weatherService) {
        this.requestHistoryRepository = requestHistoryRepository;
        this.weatherService = weatherService;
    }

    @Transactional
    public void saveRequestHistory(RequestHistory requestHistory) {
        requestHistoryRepository.save(requestHistory);
    }
}
