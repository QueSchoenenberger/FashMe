package com.lepquold.service;

import com.lepquold.model.WeatherInfo;

/**
 * Interface for handling callbacks related to weather information retrieval.
 */
public interface WeatherInfoListener {

    /**
     * Callback method invoked when weather information is successfully received.
     *
     * @param weatherInfo The received weather information.
     */
    void onWeatherInfoReceived(WeatherInfo weatherInfo);

    /**
     * Callback method invoked when an error occurs during the weather API call.
     *
     * @param e The exception representing the API call error.
     */
    void onWeatherApiError(Exception e);
}
