# LucidLight 360 - Arquitetura Orientada a Serviços

Este projeto demonstra uma aplicação baseada em **Arquitetura Orientada a Serviços (SOA)**, com foco em interoperabilidade entre APIs RESTful e SOAP, modularização de serviços e separação clara de responsabilidades.

## 🚀 Objetivos

* Simular um sistema de gestão de incidentes energéticos.
* Consumir serviços REST e SOAP.
* Utilizar boas práticas de arquitetura modular.
* Integrar diversas fontes de dados por meio de uma fachada unificada.

---

## 📦 Tecnologias Utilizadas

* Java 21
* Maven
* API REST (mock)
* API SOAP (mock)
* JSON/XML
* Padrões: REST, SOAP, WSDL
* Camadas: DTO, Serviço, Cliente, Fachada

---

## 🧱 Estrutura do Projeto

```
GS-SOA_WebServices/
├── client/
│   ├── rest/
│   │   └── WeatherApiClient, InternalApiClient
│   └── soap/
│       └── LegacySoapClient
├── config/
│   └── MockServiceConfig.java
├── dto/
│   └── IncidentReport, WeatherInfo, SensorData, AlertMessage, UserDTO
├── exception/
├── facade/
│   └── ApiFacade.java
├── service/
│   └── InternalApiService, LegacySystemService
├── main/
│   └── MainApplication.java
```

---

## ⚙️ Funcionalidades

* ✅ **API REST - Clima**

  * Consulta de clima por cidade.
* ✅ **API REST Interna**

  * Cadastro e listagem de incidentes.
  * Envio e consulta de leituras de sensores.
  * Criação e listagem de alertas.
  * Gerenciamento de usuários.
* ✅ **API SOAP Legada**

  * Verificação de status de sistema legado.
  * Atualização de registros legados.

---

## 🧪 Como executar

1. Clone o repositório:

   ```bash
   git clone https://github.com/phfarath/FIAP-GS1-SOA-LucidLight.git
   ```

2. Compile e execute:

   ```bash
   cd GS-SOA_WebServices
   mvn clean install
   mvn exec:java -Dexec.mainClass="main.MainApplication"
   ```

3. Verifique a saída no console com os testes simulados.

---

## 🧑‍💻 Integrantes do Grupo

* Pedro Henrique Farath - 98608
* Lucca VIlaça - 551538

---

## 📄 Licença

Projeto acadêmico - FIAP 2025 - Engenharia de Software.
