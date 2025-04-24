package com.example.weatherapp.repository;

import com.example.weatherapp.model.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileModel, Long> {

}
