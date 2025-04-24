package com.example.weatherapp.convert;

import com.example.weatherapp.model.RequestHistoryModel;
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
    public RequestHistoryDto toDto(RequestHistoryModel requestHistoryModel) {

        return modelMapper.map(requestHistoryModel, RequestHistoryDto.class);
    }

    @Override
    public RequestHistoryModel toEntity(RequestHistoryDto requestHistoryDto) {
        return modelMapper.map(requestHistoryDto, RequestHistoryModel.class);
    }
}
