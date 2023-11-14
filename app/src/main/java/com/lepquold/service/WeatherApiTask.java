package com.lepquold.service;

import android.os.AsyncTask;

import com.lepquold.model.WeatherInfo;

import java.io.IOException;

/**
 * AsyncTask for making asynchronous weather API calls.
 */
public class WeatherApiTask extends AsyncTask<String, Void, WeatherInfo> {
    private WeatherInfoListener listener;

    /**
     * Constructor for WeatherApiTask.
     *
     * @param listener The listener to handle weather information callbacks.
     */
    public WeatherApiTask(WeatherInfoListener listener) {
        this.listener = listener;
    }

    /**
     * Background task to fetch weather information from the API.
     *
     * @param locations The location(s) for which weather information is requested.
     * @return          The fetched weather information.
     */
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

    /**
     * Executed on the UI thread after the background task is complete.
     *
     * @param result The result of the background task, i.e., the weather information.
     */
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
