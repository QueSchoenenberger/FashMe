package com.lepquold.service;

import android.os.AsyncTask;

import com.lepquold.model.WeatherInfo;

import java.io.IOException;

public class WeatherApiTask extends AsyncTask<String, Void, WeatherInfo> {
    private WeatherInfoListener listener;

    public WeatherApiTask(WeatherInfoListener listener) {
        this.listener = listener;
    }
    @Override
    protected WeatherInfo doInBackground(String... locations) {
        try {
            WeatherService ws = new WeatherService();
            return ws.getTemperatureAndRainStatus(locations[0]);
        } catch (IOException e) {
            // Handle exception appropriately, for example, log the error
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(WeatherInfo result) {
        if (result != null) {
            // Notify the activity with the weather information
            listener.onWeatherInfoReceived(result);
        } else {
            // Notify the activity about the API call error
            listener.onWeatherApiError(new IOException("API call unsuccessful"));
        }
    }
}
