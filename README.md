# Spring boot app

Spring Boot + MySQL + JPA/Hibernate + Thymeleaf + REST API + Postman.

## Requirements
- JDK 17+
- Maven
- MySQL 8+
- VS Code
- Postman

## 1. Create the database
Open MySQL Workbench and run:

CREATE DATABASE service_management;

Do not run the sample INSERT statements until Spring Boot has created the table, or run the complete `database.sql` after the first application startup.

## 2. Configure MySQL
Open:
src/main/resources/application.properties

Set the database credentials as environment variables before starting the app.

PowerShell:

$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"

The application defaults to the `root` username when `DB_USERNAME` is omitted. Do not commit passwords to this repository.

If your MySQL username is not `root`, set `DB_USERNAME` to the correct username.

## 3. Run in VS Code terminal

Windows:
mvnw.cmd spring-boot:run

If Maven is installed globally:
mvn spring-boot:run

Or run ServiceApplication.java using the VS Code Run button.

## 4. Open the website

http://localhost:8080/

Requests:
http://localhost:8080/requests

REST API:
GET    http://localhost:8080/api/requests
GET    http://localhost:8080/api/requests/1
POST   http://localhost:8080/api/requests
PUT    http://localhost:8080/api/requests/1
DELETE http://localhost:8080/api/requests/1

## POST JSON example

{
  "title": "Laptop service required",
  "category": "Maintenance",
  "description": "Laptop is not starting properly.",
  "priority": "High",
  "status": "Pending",
  "customerName": "Dharshan",
  "customerEmail": "dharshan@example.com"
}

## PUT JSON example

{
  "title": "Laptop service completed",
  "category": "Maintenance",
  "description": "Laptop issue has been checked.",
  "priority": "Medium",
  "status": "Resolved",
  "customerName": "Dharshan",
  "customerEmail": "dharshan@example.com"
}
