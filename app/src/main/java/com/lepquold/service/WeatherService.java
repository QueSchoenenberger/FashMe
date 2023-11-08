package com.lepquold.service;

import com.lepquold.model.OutfitRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    public OutfitRequest getTemperatureForLocation(String location) throws IOException {
        String apiKey = "02bbb8486b3214186caf10ca41f9defe";
        URL url = new URL("http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + location);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONObject response = new JSONObject(result.toString());

            double temperature = response.getJSONObject("current").getDouble("temp_c");
            String conditionText = response.getJSONObject("current").getJSONObject("condition").getString("text");
            boolean isRaining = conditionText.toLowerCase().contains("rain");

            OutfitRequest request = new OutfitRequest();
            request.temperature = temperature;
            request.isRaining = isRaining;

            return request;
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        } finally {
            urlConnection.disconnect();
        }
    }
}
