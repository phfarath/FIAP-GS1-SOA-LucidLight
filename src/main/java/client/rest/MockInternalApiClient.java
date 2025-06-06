package client.rest;

import config.MockServiceConfig;
import exception.ApiClientException;
import service.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockInternalApiClient extends InternalApiClient {

    public MockInternalApiClient() {
        super("http://localhost:8080/api");
    }

    @Override
    public IncidentReport reportIncident(IncidentReport report) {
        Map<String, IncidentReport> incidents = MockServiceConfig.getIncidents();
        String newId = MockServiceConfig.generateId("INC");
        report.setId(newId);
        incidents.put(newId, report);
        return report;
    }

    @Override
    public List<IncidentReport> listIncidents() {
        return new ArrayList<>(MockServiceConfig.getIncidents().values());
    }

    @Override
    public IncidentReport getIncidentDetails(String id) throws ApiClientException {
        IncidentReport incident = MockServiceConfig.getIncidents().get(id);
        if (incident == null) {
            throw new ApiClientException("Incident not found with ID: " + id);
        }
        return incident;
    }

    @Override
    public void sendSensorReading(SensorData sensorData) {
        MockServiceConfig.getSensors().put(sensorData.getSensorId(), sensorData);
    }

    @Override
    public SensorData getSensorReadingById(String id) throws ApiClientException {
        SensorData sensor = MockServiceConfig.getSensors().get(id);
        if (sensor == null) {
            throw new ApiClientException("Sensor not found with ID: " + id);
        }
        return sensor;
    }

    @Override
    public List<SensorData> listActiveSensors() {
        return new ArrayList<>(MockServiceConfig.getSensors().values());
    }

    @Override
    public AlertMessage createAlert(AlertMessage alertMessage) {
        String newId = MockServiceConfig.generateId("ALERT");
        alertMessage.setId(newId);
        MockServiceConfig.getAlerts().put(newId, alertMessage);
        return alertMessage;
    }

    @Override
    public List<AlertMessage> listAlerts() {
        return new ArrayList<>(MockServiceConfig.getAlerts().values());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String newId = MockServiceConfig.generateId("USER");
        userDTO.setId(newId);
        MockServiceConfig.getUsers().put(newId, userDTO);
        return userDTO;
    }

    @Override
    public List<UserDTO> listUsers() {
        return new ArrayList<>(MockServiceConfig.getUsers().values());
    }
}