#Translation Management Service (TMS)

A simple API-driven service built with Spring Boot 3 and PostgreSQL to manage translations for multiple locales with tagging support.

#Features
- Store translations for multiple locales
- Tag translations for context (e.g., web, mobile)
- REST API endpoints for CRUD operations
- OAuth2 security (configurable)
- Docker and Docker Compose setup

#Technologies Used
- Java 17
- Spring Boot 3
- PostgreSQL
- Docker / Docker Compose
- Swagger / Springdoc OpenAPI

#How to Run Locally

1. Build the project:
mvn clean package

2. Start with Docker Compose:
docker-compose up --build

3. Access the API:
http://localhost:5555

4. Access Swagger UI:
http://localhost:5555/swagger-ui.html

#API Endpoints

Example endpoints:

- `POST /api/translations`
- `GET /api/translations/{id}`
- `PUT /api/translations/{id}`
- `DELETE /api/translations/{id}`

#Database Configuration

Default credentials (configured in `application.properties`):
- Database: `tms_db`
- Username: `tms`
- Password: `tms`

#License
This project is for educational and demo purposes.
