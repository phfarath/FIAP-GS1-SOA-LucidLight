package service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// DTO for Weather API Response (more detailed for OpenWeatherMap)
// Ignoring unknown properties to avoid errors if the API adds new fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {

    private MainInfo main;
    private Weather[] weather;
    private String cityName;

    @JsonProperty("name")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public MainInfo getMain() {
        return main;
    }

    public void setMain(MainInfo main) {
        this.main = main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    // Convenience method to get the primary weather description
    public String getPrimaryDescription() {
        if (weather != null && weather.length > 0) {
            return weather[0].getDescription();
        }
        return "N/A";
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
               "cityName=\'" + cityName + "\', " +
               "description=\'" + getPrimaryDescription() + "\', " +
               "main=" + main +
               "}";
    }

    // Nested class for the 'main' part of the JSON response
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainInfo {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int humidity;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
        public double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }

        @Override
        public String toString() {
            return "{temp=" + temp + ", feelsLike=" + feelsLike + ", humidity=" + humidity + "}";
        }
    }

    // Nested class for the 'weather' array elements
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String description;
        private String icon;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }
}

