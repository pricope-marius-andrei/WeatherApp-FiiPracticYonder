package com.example.weatherapp.dto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

public class WeatherDto {

    @Embedded
    private Location location;
    @Embedded
    private Current current;

    public WeatherDto(Location location, Current current) {
        this.location = location;
        this.current = current;
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return "WeatherDto{" +
                "location=" + location +
                ", current=" + current +
                '}';
    }
}

@Embeddable
class Location {
    private String name;
    private String region;
    private String country;
    private String tz_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTz_id() {
        return tz_id;
    }

    public void setTz_id(String tz_id) {
        this.tz_id = tz_id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", tz_id='" + tz_id + '\'' +
                '}';
    }
}

@Embeddable
class Current {
    private double temp_c;
    private double wind_kph;
    private String wind_dir;
    private double precip_mm;

    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public double getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(double wind_kph) {
        this.wind_kph = wind_kph;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public double getPrecip_mm() {
        return precip_mm;
    }

    public void setPrecip_mm(double precip_mm) {
        this.precip_mm = precip_mm;
    }

    @Override
    public String toString() {
        return "Current{" +
                "temp_c=" + temp_c +
                ", wind_kph=" + wind_kph +
                ", wind_dir='" + wind_dir + '\'' +
                ", precip_mm=" + precip_mm +
                '}';
    }
}

