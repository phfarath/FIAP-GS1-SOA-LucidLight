package service;

import client.soap.LegacySoapClient;
import exception.ApiClientException;

// Service layer for interacting with the Legacy SOAP API
public class LegacySoapService {

    private final LegacySoapClient soapClient;

    public LegacySoapService(LegacySoapClient soapClient) {
        this.soapClient = soapClient;
    }

    /**
     * Gets status from the legacy system.
     * Note: Uses placeholder implementation until SOAP client is generated.
     *
     * @param systemId Identifier for the system/sensor.
     * @return Status string (placeholder).
     * @throws ApiClientException If communication fails.
     */
    public String getSystemStatus(String systemId) throws ApiClientException {
        if (soapClient == null) {
            throw new ApiClientException("Legacy SOAP Client not initialized. Generate classes from WSDL first.");
        }
        // Add any business logic/validation if needed
        return soapClient.getStatus(systemId);
    }

    /**
     * Updates a record in the legacy system.
     * Note: Uses placeholder implementation until SOAP client is generated.
     *
     * @param recordId ID of the record to update.
     * @param data     Data to update.
     * @return True if update was successful (placeholder).
     * @throws ApiClientException If communication fails.
     */
    public boolean updateRecord(String recordId, String data) throws ApiClientException {
         if (soapClient == null) {
            throw new ApiClientException("Legacy SOAP Client not initialized. Generate classes from WSDL first.");
        }
        // Add any business logic/validation if needed
        return soapClient.updateRecord(recordId, data);
    }
}

