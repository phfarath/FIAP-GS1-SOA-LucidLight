package client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exception.ApiClientException;
import service.dto.AlertMessage;
import service.dto.IncidentReport;
import service.dto.SensorData;
import service.dto.UserDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class InternalApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl; // Should be https://api.lucidlight.com/api

    public InternalApiClient(String baseUrl) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        // Register JavaTimeModule for LocalDateTime serialization/deserialization
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.baseUrl = baseUrl != null && !baseUrl.isBlank() ? baseUrl : "https://api.lucidlight.com/api"; // Use provided base URL
    }

    // --- Incidents --- //

    public IncidentReport reportIncident(IncidentReport report) throws ApiClientException {
        try {
            String requestBody = objectMapper.writeValueAsString(report);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/incidents"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                // Assuming the API returns the created incident with its ID
                return objectMapper.readValue(response.body(), IncidentReport.class);
            } else {
                throw new ApiClientException(String.format("Failed to report incident. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (JsonProcessingException e) {
            throw new ApiClientException("Error serializing incident report to JSON", e);
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /incidents", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "reporting incident");
            return null; // Should not be reached due to exception handling
        }
    }

    public List<IncidentReport> listIncidents() throws ApiClientException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/incidents"))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<IncidentReport>>() {});
            } else {
                throw new ApiClientException(String.format("Failed to list incidents. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /incidents", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "listing incidents");
            return null; // Should not be reached
        }
    }

    public IncidentReport getIncidentDetails(String id) throws ApiClientException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/incidents/" + id))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), IncidentReport.class);
            } else {
                throw new ApiClientException(String.format("Failed to get incident details for ID %s. Status: %d, Response: %s",
                        id, response.statusCode(), response.body()));
            }
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /incidents/" + id, e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "getting incident details for ID " + id);
            return null; // Should not be reached
        }
    }

    // --- Sensors --- //

    public void sendSensorReading(SensorData sensorData) throws ApiClientException {
        try {
            String requestBody = objectMapper.writeValueAsString(sensorData);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/sensors/data"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new ApiClientException(String.format("Failed to send sensor data. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
            // Assuming 2xx status means success, no body expected
        } catch (JsonProcessingException e) {
            throw new ApiClientException("Error serializing sensor data to JSON", e);
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /sensors/data", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "sending sensor data");
        }
    }

    public SensorData getSensorReadingById(String id) throws ApiClientException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/sensors/" + id))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), SensorData.class);
            } else {
                throw new ApiClientException(String.format("Failed to get sensor reading for ID %s. Status: %d, Response: %s",
                        id, response.statusCode(), response.body()));
            }
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /sensors/" + id, e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "getting sensor reading for ID " + id);
            return null; // Should not be reached
        }
    }

    public List<SensorData> listActiveSensors() throws ApiClientException { // Assuming it returns SensorData list
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/sensors"))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<SensorData>>() {});
            } else {
                throw new ApiClientException(String.format("Failed to list active sensors. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /sensors", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "listing active sensors");
            return null; // Should not be reached
        }
    }

    // --- Alerts --- //

    public AlertMessage createAlert(AlertMessage alertMessage) throws ApiClientException {
        try {
            String requestBody = objectMapper.writeValueAsString(alertMessage);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/alerts"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                // Assuming the API returns the created alert with its ID
                return objectMapper.readValue(response.body(), AlertMessage.class);
            } else {
                throw new ApiClientException(String.format("Failed to create alert. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (JsonProcessingException e) {
            throw new ApiClientException("Error serializing alert message to JSON", e);
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /alerts", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "creating alert");
            return null; // Should not be reached
        }
    }

    public List<AlertMessage> listAlerts() throws ApiClientException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/alerts"))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<AlertMessage>>() {});
            } else {
                throw new ApiClientException(String.format("Failed to list alerts. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /alerts", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "listing alerts");
            return null; // Should not be reached
        }
    }

    // --- Users --- //

    public UserDTO createUser(UserDTO userDTO) throws ApiClientException {
        try {
            String requestBody = objectMapper.writeValueAsString(userDTO);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/users"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                // Assuming the API returns the created user with its ID
                return objectMapper.readValue(response.body(), UserDTO.class);
            } else {
                throw new ApiClientException(String.format("Failed to create user. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (JsonProcessingException e) {
            throw new ApiClientException("Error serializing user DTO to JSON", e);
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /users", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "creating user");
            return null; // Should not be reached
        }
    }

    public List<UserDTO> listUsers() throws ApiClientException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/users"))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<UserDTO>>() {});
            } else {
                throw new ApiClientException(String.format("Failed to list users. Status: %d, Response: %s",
                        response.statusCode(), response.body()));
            }
        } catch (URISyntaxException e) {
            throw new ApiClientException("Invalid internal API URL format for /users", e);
        } catch (IOException | InterruptedException e) {
            handleHttpException(e, "listing users");
            return null; // Should not be reached
        }
    }

    // --- Helper for Exception Handling ---
    private void handleHttpException(Exception e, String operation) throws ApiClientException {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        throw new ApiClientException("Error communicating with internal API while " + operation, e);
    }
}