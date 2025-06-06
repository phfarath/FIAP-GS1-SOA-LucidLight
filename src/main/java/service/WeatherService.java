package service;

import client.rest.WeatherApiClient;
import exception.ApiClientException;
import service.dto.WeatherInfo;

// Service layer for interacting with the Weather API
public class WeatherService {

    private final WeatherApiClient apiClient;

    public WeatherService(WeatherApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public WeatherInfo getWeather(String city) throws ApiClientException {
        // Add any business logic/validation if needed
        return apiClient.getWeatherByCity(city);
    }
}

