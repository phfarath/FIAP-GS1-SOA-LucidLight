package service;

import client.rest.InternalApiClient;
import exception.ApiClientException;
import service.dto.AlertMessage;
import service.dto.IncidentReport;
import service.dto.SensorData;
import service.dto.UserDTO;

import java.util.List;

// Service layer for interacting with the Internal LucidLight API
public class InternalApiService {

    private final InternalApiClient apiClient;

    public InternalApiService(InternalApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // --- Incidents --- //
    public IncidentReport reportIncident(IncidentReport report) throws ApiClientException {
        // Add any business logic/validation before calling the client
        return apiClient.reportIncident(report);
    }

    public List<IncidentReport> getAllIncidents() throws ApiClientException {
        return apiClient.listIncidents();
    }

    public IncidentReport getIncidentById(String id) throws ApiClientException {
        return apiClient.getIncidentDetails(id);
    }

    // --- Sensors --- //
    public void submitSensorData(SensorData sensorData) throws ApiClientException {
        // Add any business logic/validation
        apiClient.sendSensorReading(sensorData);
    }

    public SensorData getSensorData(String sensorId) throws ApiClientException {
        return apiClient.getSensorReadingById(sensorId);
    }

    public List<SensorData> getActiveSensors() throws ApiClientException {
        return apiClient.listActiveSensors();
    }

    // --- Alerts --- //
    public AlertMessage sendAlert(AlertMessage alertMessage) throws ApiClientException {
        // Add any business logic/validation
        return apiClient.createAlert(alertMessage);
    }

    public List<AlertMessage> getAllAlerts() throws ApiClientException {
        return apiClient.listAlerts();
    }

    // --- Users --- //
    public UserDTO registerUser(UserDTO userDTO) throws ApiClientException {
        // Add any business logic/validation (e.g., password hashing if handled here)
        return apiClient.createUser(userDTO);
    }

    public List<UserDTO> getAllUsers() throws ApiClientException {
        return apiClient.listUsers();
    }
}

