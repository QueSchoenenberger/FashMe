package com.lepquold.model;

public class Geocoding {
    private double latitude;
    private double longitude;

    public Geocoding(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
