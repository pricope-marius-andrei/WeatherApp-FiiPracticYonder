package com.example.weatherapp.service;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.repository.RequestHistoryRepository;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import org.springframework.stereotype.Service;

@Service
public class RequestHistoryServiceImpl implements RequestHistoryService {

    private final RequestHistoryRepository requestHistoryRepository;

    public RequestHistoryServiceImpl(RequestHistoryRepository requestHistoryRepository) {
        this.requestHistoryRepository = requestHistoryRepository;
    }

    @Override
    public void addRequestHistory(RequestHistory requestHistory) {
        requestHistoryRepository.save(requestHistory);
    }

    @Override
    public RequestHistory getRequestHistoryById(Long id) {
        return null;
    }

    @Override
    public void deleteRequestHistoryById(Long id) {

    }

    @Override
    public void updateRequestHistory(Long id, RequestHistoryDto requestHistory) {

    }
}
