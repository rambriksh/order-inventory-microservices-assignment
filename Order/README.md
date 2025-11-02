# 📦 Order Service

A Spring Boot microservice for placing product orders and updating inventory batches. Designed for modular enterprise systems with RESTful architecture and H2-based testing.

----------------------------------------------
# 🚀 Features
- Place orders via REST API
- Fetch inventory batches from Inventory Service
- Update inventory quantities across multiple batches
- Persist orders in H2 or external DB
- Exception handling for insufficient inventor

-----------------------------------------
# 🛠️ Project Setup Instructions

## ✅ Prerequisites
- Java 17+
- Maven or Gradle
- IDE (IntelliJ, Eclipse, VS Code)

## ✅ Clone and Run
- git clone https://github.com/your-username/order-inventory-microservices-assignment.git
  cd order-inventory-microservices-assignment/inventory-service
  ./mvnw spring-boot:run

## ✅ Access H2 Console
- URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave blank)
---------------------------

# 📂 Project Structure
com.example.Order
├── controller       # REST endpoints
├── service          # Business logic
├── repository       # JPA interfaces
├── model            # Entity classes
├── dto              # Request/response payloads
├── config           # External service URLs
└── resources
├── application.properties
├── schema.sql
└── data.sql


-------------------------------------------

# 📨 API Endpoint

POST /order
Place a new order and update inventory.
Request Body:

{
"productId": 1,
"quantity": 5
}

Response:
{
"id": 1,
"productId": 1,
"quantity": 5,
"orderDate": "2025-11-02T11:30:00"
}

-------------------------------------

# 🧪 Testing

## ✅ Unit Tests

- OrderServiceImplTest: Uses JUnit 5 + Mockito
- Mocks RestTemplate, OrderRepository, and Configuration
## ✅ Integration Tests
- 
- OrderIntegrationTest: Uses @SpringBootTest + H2
- Verifies full flow: API → Service → DB → Inventory update
## ✅ Controller Tests
- 
- OrderControllerTest: Uses @WebMvcTest + MockMvc
- Validates /order endpoint behavior
----------------------------------------------
# ⚙️ Configuration

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
## Enable the following property when running integration tests
#spring.jpa.hibernate.ddl-auto=create-drop

#inventory service url
inventory.service.url=http://localhost:8080/inventory/

server.port=8081

--------------------------------------------
📦 Build & Run
# Build the project
mvn clean install

# Run the service
mvn spring-boot:run


Access H2 console at: http://localhost:8081/h2-console


#CURL

POST:

curl --location 'http://localhost:8081/order' \
--header 'Content-Type: application/json' \
--data '{
"productId": 1,
"quantity": 3

}'












