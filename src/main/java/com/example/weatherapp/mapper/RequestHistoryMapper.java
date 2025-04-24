package com.example.weatherapp.mapper;

import com.example.weatherapp.model.RequestHistoryModel;
import com.example.weatherapp.dto.RequestHistoryDto;

public interface RequestHistoryMapper {
    RequestHistoryDto toDto(RequestHistoryModel requestHistoryModel);
    RequestHistoryModel toEntity(RequestHistoryDto requestHistoryDto);
}
