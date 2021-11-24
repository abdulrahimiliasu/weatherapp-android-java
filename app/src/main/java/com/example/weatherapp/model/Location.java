package com.example.weatherapp.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Location {

    String title;
    String type;
    int woeId;
    String position;
    String timezone;
    LocalTime time;
    LocalDateTime sunrise;
    LocalDateTime sunset;
    String timezoneName;

    public Location(String title, String type, int woeId, String position, String timezone, LocalTime time, LocalDateTime sunrise, LocalDateTime sunset, String timezoneName) {
        this.title = title;
        this.type = type;
        this.woeId = woeId;
        this.position = position;
        this.timezone = timezone;
        this.time = time;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.timezoneName = timezoneName;
    }
}
