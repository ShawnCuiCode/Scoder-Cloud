# Scoder Backend System Architecture

This document provides an overview of the backend structure of the **Scoder** platform, a smart matching system for student developers.

---

## Project Structure

```text
Scoder-Cloud/                         # Root directory of the backend system
├── bin/                              # Compiled binary files
├── images/                           # Image assets related to the system
├── logs/                             # System logs for debugging and monitoring
│
├── scoder-api/                       # API service layer for inter-module communication
│   ├── scoder-api-file/              # File management API interface
│   ├── scoder-api-im/                # Instant messaging API interface
│   ├── scoder-api-user/              # User management API interface
│
├── scoder-auth/                      # Authentication and authorization module
│   ├── Handles token issuance, login validation, and user authentication
│
├── scoder-common/                    # Common utility libraries and middleware support
│   ├── scoder-common-core/           # Core utils (annotations, constants, exceptions)
│   ├── scoder-common-datascope/      # Data scope control logic
│   ├── scoder-common-datasource/     # Data source configuration and management
│   ├── scoder-common-log/            # Logging infrastructure
│   ├── scoder-common-security/       # Security-related helpers (JWT, encryption)
│   ├── scoder-common-swagger/        # Swagger configuration for API documentation
│
├── scoder-gateway/                   # API Gateway for routing, load balancing, and security
│   ├── Configures route forwarding, filters, and exception handling
│
├── scoder-modules/                   # Core business logic microservices
│   ├── scoder-file/                  # File upload/download, storage management
│   ├── scoder-im/                    # WebSocket-based instant messaging service
│   ├── scoder-projects/              # Project and team creation, management, and matching logic
│
├── scoder-visual/                    # Data visualization and analytics module (under development)
│
├── pom.xml                           # Root Maven configuration file
├── README.md                         # Project documentation
├── .gitignore                        # Git ignore rules
```

---

## Key Technologies

| Layer            | Technology Stack                            |
|------------------|---------------------------------------------|
| Framework        | Spring Boot, Spring Cloud Alibaba           |
| API Management   | Spring Cloud Gateway, Swagger               |
| Communication    | RESTful APIs, OpenFeign                     |
| Database         | MySQL (relational), MongoDB (NoSQL), Redis  |
| Build Tool       | Maven                                       |
| Messaging        | WebSocket, Netty                            |
| Authentication   | JWT, OAuth 2.0                              |
| Deployment       | Alibaba Cloud                               |

---

## Module Overview

### `scoder-api`
Defines external service contracts (via OpenFeign) to enable communication between microservices such as file handling, user info, and messaging.

### `scoder-auth`
Manages user login, token generation, and permission validation. Key classes include:
- `TokenController`: issues access tokens
- `SysLoginService`: processes login credentials

### `scoder-common`
Reusable utilities and components, including:
- `StringUtils`, `FileUtils`: general-purpose helpers
- `RepeatSubmit`: anti-duplicate request annotation
- `SwaggerConfig`: documentation support

### `scoder-gateway`
Acts as the single entry point to the platform. Handles routing, authentication, blacklist filtering, and CORS configuration.

### `scoder-modules`
Contains the core business services:
- **File Module**: supports upload, download, and metadata persistence.
- **IM Module**: real-time messaging, group chat, and WebSocket handling.
- **Projects Module**: manages teams and projects, runs the intelligent matching algorithm.

### `scoder-visual`
Planned for future use to support admin dashboard and visual analytics.

---

## Running the System
To run the backend system:

```bash
# Build the project
mvn clean install

# Start individual services
java -jar scoder-gateway/target/*.jar
java -jar scoder-auth/target/*.jar
java -jar scoder-modules/scoder-projects/target/*.jar
# ... etc
```

> **Note:** Configuration files such as `application.yml` or `bootstrap.yml` must be provided with correct database and Redis credentials before deployment.

---

## Authors & License
- Author: Xiang Cui
- License: MIT (or custom, if defined)

---