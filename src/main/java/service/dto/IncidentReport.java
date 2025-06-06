package service.dto;

import java.time.LocalDateTime;

// Example DTO for Incident Report
public class IncidentReport {
    private String id; // Optional, might be assigned by the server
    private String description;
    private String location;
    private String severity; // e.g., LOW, MEDIUM, HIGH
    private LocalDateTime timestamp;

    // Constructors, Getters, Setters
    public IncidentReport() {
        this.timestamp = LocalDateTime.now();
    }

    public IncidentReport(String description, String location, String severity) {
        this.description = description;
        this.location = location;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "IncidentReport{" +
               "id='" + id + '\'' +
               ", description='" + description + '\'' +
               ", location='" + location + '\'' +
               ", severity='" + severity + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}

