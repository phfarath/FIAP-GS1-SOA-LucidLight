package service.dto;

import java.time.LocalDateTime;

// Example DTO for Alert Message
public class AlertMessage {
    private String id; // Optional, might be assigned by the server
    private String message;
    private String targetUser; // Could be a user ID or group name
    private String type; // e.g., POWER_OUTAGE_WARNING, SYSTEM_UPDATE
    private LocalDateTime timestamp;

    // Constructors, Getters, Setters
    public AlertMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public AlertMessage(String message, String targetUser, String type) {
        this.message = message;
        this.targetUser = targetUser;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AlertMessage{" +
               "id=\'" + id + "\', " +
               "message=\'" + message + "\', " +
               "targetUser=\'" + targetUser + "\', " +
               "type=\'" + type + "\', " +
               "timestamp=" + timestamp +
               "}";
    }
}

