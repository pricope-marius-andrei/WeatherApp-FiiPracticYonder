package com.example.weatherapp.repository;

import com.example.weatherapp.dao.RequestHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {

    @Query("SELECT r FROM RequestHistory r WHERE r.user.id = :userId")
    Page<RequestHistory> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
