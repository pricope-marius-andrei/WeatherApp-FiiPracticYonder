package com.example.weatherapp.repository;

import com.example.weatherapp.dao.User;
import com.example.weatherapp.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.userProfile.id = ?1")
    User findByProfileId(Long profileId);
}
