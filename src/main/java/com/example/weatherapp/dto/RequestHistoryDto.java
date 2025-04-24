package com.example.weatherapp.dto;

public class RequestHistoryDto {

    private Long id;

    private double lat;

    private double lon;

    private String response;

    private Boolean q;

    private Boolean aqi;

    private Integer days;

    private Boolean alerts;

    public RequestHistoryDto(Long id, double lat, double lon, String response, Boolean q, Boolean aqi, Integer days, Boolean alerts) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.response = response;
        this.q = q;
        this.aqi = aqi;
        this.days = days;
        this.alerts = alerts;
    }

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
}
