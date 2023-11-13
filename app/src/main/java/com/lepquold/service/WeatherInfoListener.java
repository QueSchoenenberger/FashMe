package com.lepquold.service;

import com.lepquold.model.WeatherInfo;

public interface WeatherInfoListener {
    void onWeatherInfoReceived(WeatherInfo weatherInfo);
    void onWeatherApiError(Exception e);
}
