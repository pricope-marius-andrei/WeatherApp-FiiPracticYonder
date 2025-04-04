package com.example.weatherapp.convert;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.dto.RequestHistoryDto;
import com.example.weatherapp.mapper.RequestHistoryMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RequestHistoryDtoConverter implements RequestHistoryMapper {

    private final ModelMapper modelMapper;

    public RequestHistoryDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RequestHistoryDto toDto(RequestHistory requestHistory) {

        return modelMapper.map(requestHistory, RequestHistoryDto.class);
    }

    @Override
    public RequestHistory toEntity(RequestHistoryDto requestHistoryDto) {
        return modelMapper.map(requestHistoryDto, RequestHistory.class);
    }
}
