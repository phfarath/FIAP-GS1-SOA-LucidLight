package client.soap;

import exception.ApiClientException;

public class MockLegacySoapClient extends LegacySoapClient {

    public MockLegacySoapClient() throws ApiClientException {
        super("http://localhost:8080/mock-wsdl", "http://mocknamespace", "MockService", "MockPort");
    }

    @Override
    public String getStatus(String systemIdentifier) {
        return "STATUS_OK - Sistema: " + systemIdentifier + " em funcionamento";
    }

    @Override
    public boolean updateRecord(String recordId, String data) {
        return true;
    }

    @Override
    protected Object createServicePort() throws ApiClientException{
        return new LegacySystemService() {
            @Override
            public String getSensorStatus(String sensorId) {
                return "SIMULADO: Sensor " + sensorId + " operacional";
            }

            @Override
            public boolean updateSensorRecord(String recordId, String data) {
                return true;
            }
        };
    }
}