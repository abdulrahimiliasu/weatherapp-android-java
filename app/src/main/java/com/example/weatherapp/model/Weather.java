package com.example.weatherapp.model;


public class Weather {
    Long id ;
    String state;
    String stateAbbreviation;
    String windCompassDirection;
    String created;
    String applicableDate;
    float minTemp;
    float maxTemp;
    float temp;
    float windSpeed;
    float windDirection;
    float airPressure;
    float humidity;
    float visibility;
    float predictability;

    public Weather(Long id, String state, String stateAbbreviation, String windCompassDirection, String created, String applicableDate, float minTemp, float maxTemp, float temp, float windSpeed, float windDirection, float airPressure, float humidity, float visibility, float predictability) {
        this.id = id;
        this.state = state;
        this.stateAbbreviation = stateAbbreviation;
        this.windCompassDirection = windCompassDirection;
        this.created = created;
        this.applicableDate = applicableDate;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.airPressure = airPressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.predictability = predictability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getWindCompassDirection() {
        return windCompassDirection;
    }

    public void setWindCompassDirection(String windCompassDirection) {
        this.windCompassDirection = windCompassDirection;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getTemp() {
        return Math.round(temp);
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(float airPressure) {
        this.airPressure = airPressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public float getPredictability() {
        return predictability;
    }

    public void setPredictability(float predictability) {
        this.predictability = predictability;
    }
}
