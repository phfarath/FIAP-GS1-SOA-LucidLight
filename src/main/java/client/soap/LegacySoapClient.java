package client.soap;

import exception.ApiClientException;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class LegacySoapClient {

    private final String wsdlUrl;
    private final String namespace;
    private final String serviceName;
    private final String portName;

    // Adicionando variável de referência ao serviço
    private LegacySystemService servicePort;

    public LegacySoapClient(String wsdlUrl, String namespace, String serviceName, String portName) throws ApiClientException {
        this.wsdlUrl = wsdlUrl;
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.portName = portName;
        // Initialize the service port
        this.servicePort = (LegacySystemService) createServicePort();
    }

    public String getStatus(String systemIdentifier) throws ApiClientException {
        System.out.println("[SOAP Client Placeholder] Requesting status for: " + systemIdentifier);
        try {
            return servicePort.getSensorStatus(systemIdentifier);
        } catch (Exception e) {
            throw new ApiClientException("Error calling legacy SOAP service for getStatus", e);
        }
    }

    public boolean updateRecord(String recordId, String data) throws ApiClientException {
        System.out.println("[SOAP Client Placeholder] Updating record: " + recordId + " with data: " + data);
        try {
            boolean success = servicePort.updateSensorRecord(recordId, data);
            return success;
        } catch (Exception e) {
            throw new ApiClientException("Error calling legacy SOAP service for updateRecord", e);
        }
    }

    protected Object createServicePort() throws ApiClientException {
        try {
            URL url = new URL(this.wsdlUrl);
            QName serviceQName = new QName(this.namespace, this.serviceName);
            QName portQName = new QName(this.namespace, this.portName);

            Service service = Service.create(url, serviceQName);
            Object port = service.getPort(portQName, Object.class);

            // Calcular URL do endpoint
            String calculatedEndpointUrl = this.wsdlUrl.replace("?wsdl", "");

            BindingProvider bp = (BindingProvider) port;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, calculatedEndpointUrl);
            bp.getRequestContext().put("jakarta.xml.ws.client.connectionTimeout", 60000);
            bp.getRequestContext().put("jakarta.xml.ws.client.receiveTimeout", 60000);

            return port;

        } catch (MalformedURLException e) {
            throw new ApiClientException("Invalid WSDL URL: " + this.wsdlUrl, e);
        } catch (Exception e) {
            throw new ApiClientException("Could not create JAX-WS port for service " + this.serviceName + " at " + this.wsdlUrl, e);
        }
    }

    @jakarta.jws.WebService(name = "SensorPort", targetNamespace = "http://lucidlight.com/sensors")
    @jakarta.xml.bind.annotation.XmlSeeAlso({})
    public interface LegacySystemService {

        @jakarta.jws.WebMethod
        @jakarta.jws.WebResult(name = "status", targetNamespace = "")
        @jakarta.xml.ws.RequestWrapper(localName = "getSensorStatus", targetNamespace = "http://lucidlight.com/sensors", className = "client.soap.generated.GetSensorStatus")
        @jakarta.xml.ws.ResponseWrapper(localName = "getSensorStatusResponse", targetNamespace = "http://lucidlight.com/sensors", className = "client.soap.generated.GetSensorStatusResponse")
        public String getSensorStatus(@jakarta.jws.WebParam(name = "sensorId", targetNamespace = "") String sensorId);

        // Adicionando método de atualização que estava faltando
        @jakarta.jws.WebMethod
        @jakarta.jws.WebResult(name = "result", targetNamespace = "")
        public boolean updateSensorRecord(@jakarta.jws.WebParam(name = "recordId", targetNamespace = "") String recordId,
                                          @jakarta.jws.WebParam(name = "data", targetNamespace = "") String data);
    }
}