package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.dto.RequestHistoryDto;

public interface RequestHistoryService {

    void addRequestHistory(RequestHistory requestHistory);

    RequestHistory getRequestHistoryById(Long id);

    void deleteRequestHistoryById(Long id);

    void updateRequestHistory(Long id, RequestHistoryDto requestHistory);
}
