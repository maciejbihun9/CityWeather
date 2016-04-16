package com.example.maciejbihun.cityweather.model;

/**
 * Created by MaciekBihun on 2016-03-29.
 */
public class CurrentWeather {

    private String city;
    private int temperature;
    private String summary;
    private String icon;
    private long time;
    private int pressure;

    private static CurrentWeather currentWeather = new CurrentWeather();
    private CurrentWeather(){

    }

    public static CurrentWeather getInstance(){
        return currentWeather;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTemperature() {
        return temperature;
    }

    public long getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }
}
