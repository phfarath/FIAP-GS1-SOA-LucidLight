package facade;

import exception.ApiClientException;
import service.InternalApiService;
import service.LegacySoapService;
import service.WeatherService;
import service.dto.*; // Import all DTOs

import java.util.List;

// Facade to simplify interactions with different API services
public class ApiFacade {

    private final WeatherService weatherService;
    private final InternalApiService internalApiService;
    private final LegacySoapService legacySoapService;

    public ApiFacade(WeatherService weatherService, InternalApiService internalApiService, LegacySoapService legacySoapService) {
        this.weatherService = weatherService;
        this.internalApiService = internalApiService;
        this.legacySoapService = legacySoapService;
    }

    // --- Weather API Methods --- //
    public WeatherInfo getWeatherForCity(String city) throws ApiClientException {
        System.out.println("Facade: Requesting weather for city: " + city);
        return weatherService.getWeather(city);
    }

    // --- Internal API Methods --- //
    public IncidentReport reportNewIncident(IncidentReport report) throws ApiClientException {
        System.out.println("Facade: Reporting new incident...");
        return internalApiService.reportIncident(report);
    }

    public List<IncidentReport> listAllIncidents() throws ApiClientException {
        System.out.println("Facade: Listing all incidents...");
        return internalApiService.getAllIncidents();
    }

    public IncidentReport getIncidentDetails(String id) throws ApiClientException {
        System.out.println("Facade: Getting details for incident ID: " + id);
        return internalApiService.getIncidentById(id);
    }

    public void sendSensorReading(SensorData sensorData) throws ApiClientException {
        System.out.println("Facade: Sending sensor reading for ID: " + sensorData.getSensorId());
        internalApiService.submitSensorData(sensorData);
    }

    public SensorData getSensorReading(String sensorId) throws ApiClientException {
        System.out.println("Facade: Getting reading for sensor ID: " + sensorId);
        return internalApiService.getSensorData(sensorId);
    }

    public List<SensorData> listActiveSensors() throws ApiClientException {
        System.out.println("Facade: Listing active sensors...");
        return internalApiService.getActiveSensors();
    }

    public AlertMessage createAlert(AlertMessage alertMessage) throws ApiClientException {
        System.out.println("Facade: Creating alert...");
        return internalApiService.sendAlert(alertMessage);
    }

    public List<AlertMessage> listAllAlerts() throws ApiClientException {
        System.out.println("Facade: Listing all alerts...");
        return internalApiService.getAllAlerts();
    }

    public UserDTO createUser(UserDTO userDTO) throws ApiClientException {
        System.out.println("Facade: Creating user: " + userDTO.getUsername());
        return internalApiService.registerUser(userDTO);
    }

    public List<UserDTO> listAllUsers() throws ApiClientException {
        System.out.println("Facade: Listing all users...");
        return internalApiService.getAllUsers();
    }

    // --- Legacy SOAP API Methods (Placeholders) --- //
    public String getLegacySystemStatus(String systemId) throws ApiClientException {
        System.out.println("Facade: Checking legacy system status for: " + systemId);
        return legacySoapService.getSystemStatus(systemId);
    }

    public boolean updateLegacySystemRecord(String recordId, String data) throws ApiClientException {
        System.out.println("Facade: Updating legacy system record: " + recordId);
        return legacySoapService.updateRecord(recordId, data);
    }
}

