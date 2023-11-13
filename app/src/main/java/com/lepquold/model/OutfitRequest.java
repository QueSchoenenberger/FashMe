package com.lepquold.model;

public class OutfitRequest {
    private double temperature;
    private boolean isRaining;
    private Style style;

    public OutfitRequest(double temperature, boolean isRaining, Style style) {
        this.temperature = temperature;
        this.isRaining = isRaining;
        this.style = style;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public Style getStyle() {
        return style;
    }
}
