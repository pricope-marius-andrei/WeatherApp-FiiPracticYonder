package com.example.weatherapp.repository;

import com.example.weatherapp.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long>{

    @Query("SELECT u FROM UserModel u WHERE u.userProfileModel.id = ?1")
    UserModel findByProfileId(Long profileId);

    @Query("SELECT u FROM UserModel u WHERE u.username = ?1")
    UserModel findByUsername(String username);
}
