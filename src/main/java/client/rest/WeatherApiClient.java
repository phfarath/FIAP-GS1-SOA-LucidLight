package client.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.dto.WeatherInfo; // Adjusted import
import exception.ApiClientException; // Adjusted import

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WeatherApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherApiClient(String apiKey) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper()
                .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("API key cannot be null or empty.");
        }
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeatherByCity(String city) throws ApiClientException {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City name cannot be null or empty.");
        }
        try {
            String uriString = String.format("%s?q=%s&appid=%s&units=metric&lang=pt_br", baseUrl, city, apiKey);
            URI uri = new URI(uriString.replace(" ", "%20"));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), WeatherInfo.class);
            } else {
                String errorMsg = String.format("Failed to fetch weather data for city '%s'. Status code: %d. Response: %s",
                                                city, response.statusCode(), response.body());
                throw new ApiClientException(errorMsg);
            }

        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid API URL format for city: " + city, e);
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new ApiClientException("Error communicating with weather API for city: " + city, e);
        }
    }
}

