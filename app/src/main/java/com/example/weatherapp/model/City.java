package com.example.weatherapp.model;

public class City {
    String title;
    String type;
    int woeId;
    String position;

    public City(String title, String type, int woeId, String position){
        this.title = title;
        this.type = type;
        this.woeId = woeId;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "City{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", woeId=" + woeId +
                ", position='" + position + '\'' +
                '}';
    }

    public int getWoeId() {
        return woeId;
    }

    public void setWoeId(int woeId) {
        this.woeId = woeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
