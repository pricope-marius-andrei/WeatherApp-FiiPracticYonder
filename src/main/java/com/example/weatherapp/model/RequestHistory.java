package com.example.weatherapp.model;

import jakarta.persistence.*;

@Entity
public class RequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "double precision")
    private double lat;

    @Column(columnDefinition = "double precision")
    private double lon;

    @Column
    private String location;

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
    private UserModel user;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean getQ() {
        return q;
    }

    public void setQ(Boolean q) {
        this.q = q;
    }

    public Boolean getAqi() {
        return aqi;
    }

    public void setAqi(Boolean aqi) {
        this.aqi = aqi;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Boolean getAlerts() {
        return alerts;
    }

    public void setAlerts(Boolean alerts) {
        this.alerts = alerts;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
