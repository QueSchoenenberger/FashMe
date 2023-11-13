package com.lepquold.service;

import com.lepquold.model.Geocoding;
import com.lepquold.model.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private static final String GEOCODING_API_URL = "https://api.openweathermap.org/geo/1.0/direct";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "02bbb8486b3214186caf10ca41f9defe";

    public WeatherInfo getTemperatureAndRainStatus(String location) throws IOException {
        Geocoding geocoding = getGeocoding(location);
        if (geocoding != null) {
            URL url = new URL(WEATHER_API_URL + "?lat=" + geocoding.getLatitude() + "&lon=" + geocoding.getLongitude() + "&appid=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return parseWeatherInfo(result.toString());
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private Geocoding getGeocoding(String location) throws IOException {
        URL url = new URL(GEOCODING_API_URL + "?q=" + location + "&limit=1&appid=" + API_KEY);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return parseGeocoding(result.toString());
        } finally {
            urlConnection.disconnect();
        }
    }

    private Geocoding parseGeocoding(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);

            if (jsonArray.length() > 0) {
                JSONObject locationObject = jsonArray.getJSONObject(0);

                double latitude = locationObject.getDouble("lat");
                double longitude = locationObject.getDouble("lon");

                return new Geocoding(latitude, longitude);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WeatherInfo parseWeatherInfo(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);

            double temperature = response.getJSONObject("main").getDouble("temp");
            JSONArray weatherArray = response.getJSONArray("weather");
            boolean isRaining = false;

            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherObject = weatherArray.getJSONObject(i);
                String main = weatherObject.getString("main").toLowerCase();
                if (main.contains("rain")) {
                    isRaining = true;
                    break;
                }
            }

            return new WeatherInfo(temperature, isRaining);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
