package com.example.weatherapp.model;

public class ParentLocation {

    String title;
    String type;
    int woeId;
    String position;

    public ParentLocation(String title, String type, int woeId, String position) {
        this.title = title;
        this.type = type;
        this.woeId = woeId;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }
}
