package com.example.maciejbihun.cityweather.model;

/**
 * Created by MaciekBihun on 2016-03-23.
 */
public class HourlyWeather {

    public HourlyWeather(){

    }
    private String time;
    private String temperature;
    private String icon;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
