package com.example.weatherapp.service.interfaces;

import com.example.weatherapp.model.RequestHistory;
import com.example.weatherapp.dto.RequestHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestHistoryService {

    void addRequestHistory(RequestHistory requestHistory);

    Page<RequestHistoryDto> getPagedRequestHistoriesByUserId(Long userId, Pageable pageable);

    RequestHistory getRequestHistoryById(Long id);

    void deleteRequestHistoryById(Long id);

    void updateRequestHistory(Long id, RequestHistoryDto requestHistory);
}
