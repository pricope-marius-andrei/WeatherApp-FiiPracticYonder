package com.example.weatherapp.dao;

import jakarta.persistence.*;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private Boolean emailNotification;

    @OneToOne
    private User user;
}
