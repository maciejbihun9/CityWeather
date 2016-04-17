package com.example.maciejbihun.cityweather.model;

/**
 * Created by MaciekBihun on 2016-03-29.
 */
public class CurrentWeather {

    private String city;
    private String temperature;
    private String summary;
    private String icon;
    private long time;
    private String pressure;

    private static CurrentWeather currentWeather = new CurrentWeather();
    private CurrentWeather(){

    }

    public static  CurrentWeather getInstance(){
        return currentWeather;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public static void setCurrentWeather(CurrentWeather currentWeather) {
        CurrentWeather.currentWeather = currentWeather;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
