package client.rest;

import config.MockServiceConfig;
import exception.ApiClientException;
import service.dto.WeatherInfo;

public class MockWeatherApiClient extends WeatherApiClient {

    public MockWeatherApiClient() {
        super("chave-simulada");
    }

    @Override
    public WeatherInfo getWeatherByCity(String city) {
        return MockServiceConfig.getMockWeather(city);
    }
}