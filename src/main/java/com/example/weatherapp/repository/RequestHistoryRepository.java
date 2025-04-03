package com.example.weatherapp.repository;

import com.example.weatherapp.dao.RequestHistory;
import com.example.weatherapp.dto.RequestHistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {
}
