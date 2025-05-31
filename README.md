# Task Management System

## Overview
A RESTful API built with **Spring Boot** that enables secure task management with JWT authentication, designed for scalability and deployable via Docker.

## Key Features
✅ **Core Requirements**
- Full CRUD operations for tasks
- Task filtering by status (Pending/Completed)
- Weekly completion percentage analytics
- Swagger API documentation

✅ **Bonus Features**
- JWT authentication with Spring Security
- Comprehensive test suite (JUnit)
- Docker-compose deployment
- Priority-based task processing

## Tech Stack
| Component               | Technology                          |
|-------------------------|-------------------------------------|
| Backend Framework       | Spring Boot 3.5.0                   |
| Language                | Java 21                             |
| Database                | H2 (Dev), PostgreSQL (Prod)         |
| Authentication          | JWT with Spring Security 6.2+       |
| API Documentation       | SpringDoc OpenAPI 2.3.0             |
| Containerization        | Docker + Docker Compose             |
| Testing                 | JUnit 5, Mockito                    |

### Local Development
```bash
# 1. Clone repository
navigate to the root
cd task-manager

# 2. Run with Maven
mvn spring-boot:run

# 3. Access endpoints:
# - API: http://localhost:8080/api
# - Swagger: http://localhost:8080/swagger-ui.html
# - H2 Console: http://localhost:8080/h2-console
