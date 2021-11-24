package com.example.weatherapp.model;

public class WeatherSource {

    String title;
    String slug;
    String url;
    int crawlRate;

    public WeatherSource(String title, String slug, String url, int crawlRate) {
        this.title = title;
        this.slug = slug;
        this.url = url;
        this.crawlRate = crawlRate;
    }

}
