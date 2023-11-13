package com.lepquold.model;

public class WeatherInfo {
    private double temperature;
    private boolean isRaining;

    public WeatherInfo(double temperature, boolean isRaining) {
        this.temperature = temperature - 273.15; // convert kelvin to celsius
        this.isRaining = isRaining;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isRaining() {
        return isRaining;
    }
}