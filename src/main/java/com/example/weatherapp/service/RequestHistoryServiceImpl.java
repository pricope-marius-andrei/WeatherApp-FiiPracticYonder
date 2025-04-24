package com.example.weatherapp.service;

import com.example.weatherapp.model.RequestHistoryModel;
import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.mapper.RequestHistoryMapper;
import com.example.weatherapp.repository.RequestHistoryRepository;
import com.example.weatherapp.service.interfaces.RequestHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestHistoryServiceImpl implements RequestHistoryService {

    private final RequestHistoryRepository requestHistoryRepository;
    private final RequestHistoryMapper requestHistoryMapper;

    public RequestHistoryServiceImpl(RequestHistoryRepository requestHistoryRepository, RequestHistoryMapper requestHistoryMapper) {
        this.requestHistoryRepository = requestHistoryRepository;
        this.requestHistoryMapper = requestHistoryMapper;
    }

    @Override
    public void addRequestHistory(RequestHistoryModel requestHistoryModel) {
        requestHistoryRepository.save(requestHistoryModel);
    }

    @Override
    public Page<RequestHistoryDto> getPagedRequestHistoriesByUserId(Long userId, Pageable pageable) {

        Page<RequestHistoryModel> requestHistories = requestHistoryRepository.findAllByUserId(userId, pageable);

        return requestHistories.map(requestHistoryMapper::toDto);
    }

    @Override
    public RequestHistoryModel getRequestHistoryById(Long id) {
        return null;
    }

    @Override
    public void deleteRequestHistoryById(Long id) {

    }

    @Override
    public void updateRequestHistory(Long id, RequestHistoryDto requestHistory) {

    }
}
