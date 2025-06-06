package config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import service.dto.*;
import java.time.LocalDateTime;

public class MockServiceConfig {
    // Dados simulados para os serviços
    private static final Map<String, IncidentReport> incidents = new HashMap<>();
    private static final Map<String, SensorData> sensors = new HashMap<>();
    private static final Map<String, AlertMessage> alerts = new HashMap<>();
    private static final Map<String, UserDTO> users = new HashMap<>();

    static {
        // Inicializa com alguns dados de exemplo
        initMockData();
    }

    private static void initMockData() {
        // Dados iniciais para incidentes
        IncidentReport incident = new IncidentReport("Falha de energia", "Bloco A", "HIGH");
        incident.setId("INC-001");
        incidents.put(incident.getId(), incident);

        // Dados iniciais para sensores
        SensorData sensor = new SensorData("TEMP-001", 22.5, "Celsius");
        sensors.put(sensor.getSensorId(), sensor);

        // Dados iniciais para alertas
        AlertMessage alert = new AlertMessage("Manutenção programada", "ALL", "MAINTENANCE");
        alert.setId("ALERT-001");
        alerts.put(alert.getId(), alert);

        // Dados iniciais para usuários
        UserDTO user = new UserDTO("admin", "admin@example.com", "ADMIN");
        user.setId("USER-001");
        users.put(user.getId(), user);
    }

    // Métodos para acessar os dados simulados
    public static Map<String, IncidentReport> getIncidents() {
        return incidents;
    }

    public static Map<String, SensorData> getSensors() {
        return sensors;
    }

    public static Map<String, AlertMessage> getAlerts() {
        return alerts;
    }

    public static Map<String, UserDTO> getUsers() {
        return users;
    }

    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static WeatherInfo getMockWeather(String city) {
        WeatherInfo info = new WeatherInfo();
        info.setCityName(city);

        WeatherInfo.MainInfo main = new WeatherInfo.MainInfo();
        main.setTemp(25.0);
        main.setFeelsLike(26.0);
        main.setHumidity(70);
        info.setMain(main);

        WeatherInfo.Weather weather = new WeatherInfo.Weather();
        weather.setDescription("Céu limpo");
        weather.setIcon("01d");
        info.setWeather(new WeatherInfo.Weather[]{weather});

        return info;
    }
}