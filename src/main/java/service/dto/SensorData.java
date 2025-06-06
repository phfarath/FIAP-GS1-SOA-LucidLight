package service.dto;

import java.time.LocalDateTime;

// Example DTO for Sensor Data
public class SensorData {
    private String sensorId;
    private double value;
    private String unit;
    private LocalDateTime timestamp;

    // Constructors, Getters, Setters
    public SensorData() {
        this.timestamp = LocalDateTime.now();
    }

    public SensorData(String sensorId, double value, String unit) {
        this.sensorId = sensorId;
        this.value = value;
        this.unit = unit;
        this.timestamp = LocalDateTime.now();
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SensorData{" +
               "sensorId=\'" + sensorId + "\'" +
               ", value=" + value +
               ", unit=\'" + unit + "\'" +
               ", timestamp=" + timestamp +
               "}";
    }
}

