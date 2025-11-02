# 📦 Inventory Service

This microservice manages product inventory and batch-level tracking with expiry dates. It is built using Spring Boot, Spring Data JPA, and an H2 in-memory database, and follows the Factory Design Pattern for future extensibility.

------------------------------------------------
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
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave blank)

-------------------------------------------------

## 📚 API Documentation
GET /inventory/{productId}
Returns a list of inventory batches for the given product, sorted by expiry date.

[
{
"id": 2,
"product": { "id": 1, "name": "Laptop" },
"quantity": 5,
"expiryDate": "2025-11-30"
},
{
"id": 1,
"product": { "id": 1, "name": "Laptop" },
"quantity": 10,
"expiryDate": "2025-12-31"
}
]

POST /inventory/update
Updates inventory by adding a new batch.

Request Body:

{
"productId": 1,
"batchId":2,
"quantity": 3,
"expiryDate": "2025-11-30"
}


Response:
Inventory updated

-------------------------------------

# 🧪 Testing Instructions

# ✅ Unit Tests
Run with:

./mvnw test

- Uses JUnit 5 and Mockito
- Tests service logic in isolation

# ✅ Integration Tests
- Annotated with @SpringBootTest
- Uses H2 for real DB interaction
- Covers REST endpoints via MockMvc

# ✅ Example Test Coverage
- InventoryServiceImplTest.java: Unit tests for service methods
- InventoryControllerTest.java: Unit tests for controller using Mockito
- InventoryIntegrationTest.java: Full-stack tests with H2 and MockMvc

----------------------------------------------

# 🏭 Architecture & Extensibility

This service uses the Factory Design Pattern to allow future extension of inventory handling logic. You can add new strategies by implementing the InventoryService interface and registering them in InventoryServiceFactory.

# 📁 Project Structure

src/
├── controller/         → REST endpoints
├── service/            → Business logic + Factory
├── repository/         → JPA repositories
├── model/              → Entity classes
├── dto/                → Request/response models
└── InventoryApplication.java


#CURL
#POST

curl --location 'http://localhost:8080/inventory/update' \
--header 'Content-Type: application/json' \
--data '{
"productId": 1,
"batchId": 2,
"quantity": 3,
"expiryDate": "2025-11-30"
}'

#GET

curl --location 'http://localhost:8080/inventory/1'
