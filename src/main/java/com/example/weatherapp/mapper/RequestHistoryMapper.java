package com.example.weatherapp.mapper;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.dto.RequestHistoryDto;

public interface RequestHistoryMapper {
    RequestHistoryDto toDto(RequestHistory requestHistory);
    RequestHistory toEntity(RequestHistoryDto requestHistoryDto);
}
