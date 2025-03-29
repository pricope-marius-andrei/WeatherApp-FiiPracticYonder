package com.example.weatherapp.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class RequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String lat;

    @Column
    private String lon;

    @Column(length = 2047)
    private String response;

    @Column
    private Boolean q;

    @Column
    private Boolean aqi;

    @Column
    private Integer days;

    @Column
    private Boolean alerts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
