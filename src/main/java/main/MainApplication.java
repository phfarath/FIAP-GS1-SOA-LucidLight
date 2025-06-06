package main;

import client.rest.MockInternalApiClient;
import client.rest.MockWeatherApiClient;
import client.soap.MockLegacySoapClient;
import exception.ApiClientException;
import facade.ApiFacade;
import service.InternalApiService;
import service.LegacySoapService;
import service.WeatherService;
import service.dto.*;

import java.util.List;

public class MainApplication {

    public static void main(String[] args) {
        try {
            System.out.println("Inicializando clientes simulados...");
            // Usando implementações simuladas em vez das reais
            MockWeatherApiClient weatherClient = new MockWeatherApiClient();
            MockInternalApiClient internalClient = new MockInternalApiClient();
            MockLegacySoapClient soapClient = new MockLegacySoapClient();

            System.out.println("Inicializando serviços...");
            WeatherService weatherService = new WeatherService(weatherClient);
            InternalApiService internalService = new InternalApiService(internalClient);
            LegacySoapService soapService = new LegacySoapService(soapClient);

            System.out.println("Inicializando fachada...");
            ApiFacade facade = new ApiFacade(weatherService, internalService, soapService);

            // O restante do código original permanece igual
            // (Os testes que você já tinha implementado)

            // 1. Weather API
            System.out.println("\n--- Testando API de Clima ---");
            WeatherInfo weather = facade.getWeatherForCity("Sao Paulo");
            System.out.println("Clima em São Paulo: " + weather);

            // 2. Internal API (REST)
            System.out.println("\n--- Testando API Interna (REST) ---");

            IncidentReport newIncident = new IncidentReport("Queda de energia no Setor B", "Rua das Flores, 123", "HIGH");
            IncidentReport createdIncident = facade.reportNewIncident(newIncident);
            System.out.println("Incidente reportado: " + createdIncident);

            List<IncidentReport> incidents = facade.listAllIncidents();
            System.out.println("Incidentes listados: " + incidents.size());
            if (!incidents.isEmpty()) {
                System.out.println("Detalhes do primeiro incidente: " + facade.getIncidentDetails(incidents.get(0).getId()));
            }

            SensorData sensorReading = new SensorData("TEMP-001", 25.5, "Celsius");
            facade.sendSensorReading(sensorReading);
            System.out.println("Leitura do sensor enviada.");

            SensorData fetchedReading = facade.getSensorReading("TEMP-001");
            System.out.println("Leitura do sensor obtida: " + fetchedReading);

            List<SensorData> sensors = facade.listActiveSensors();
            System.out.println("Sensores ativos listados: " + sensors.size());

            AlertMessage newAlert = new AlertMessage("Manutenção programada para 03:00", "ALL_OPERATORS", "MAINTENANCE");
            AlertMessage createdAlert = facade.createAlert(newAlert);
            System.out.println("Alerta criado: " + createdAlert);

            List<AlertMessage> alerts = facade.listAllAlerts();
            System.out.println("Alertas listados: " + alerts.size());

            UserDTO newUser = new UserDTO("tech01", "tech01@lucidlight.com", "TECHNICIAN");
            UserDTO createdUser = facade.createUser(newUser);
            System.out.println("Usuário criado: " + createdUser);

            List<UserDTO> users = facade.listAllUsers();
            System.out.println("Usuários listados: " + users.size());

            // 3. Legacy SOAP API
            System.out.println("\n--- Testando API SOAP Legada ---");
            String systemId = "SYS-C";
            String legacyStatus = facade.getLegacySystemStatus(systemId);
            System.out.println("Status do Sistema Legado " + systemId + ": " + legacyStatus);

            boolean updated = facade.updateLegacySystemRecord("REC-456", "<data>final</data>");
            System.out.println("Registro legado atualizado: " + updated);

        } catch (ApiClientException e) {
            System.err.println("Erro ao chamar API: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nExecução finalizada.");
    }
}