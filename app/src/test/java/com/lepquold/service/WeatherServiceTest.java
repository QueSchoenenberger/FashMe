package com.lepquold.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

import java.io.IOException;

public class WeatherServiceTest {

    //This Test is testing if the api calls are working
    @Test
    public void getTemperatureAndRainStatus() throws IOException {
        WeatherService ws = new WeatherService();
        assertNotNull(ws.getTemperatureAndRainStatus("Zuerich"));
    }
}