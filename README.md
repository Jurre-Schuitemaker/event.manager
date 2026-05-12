# Event Manager

A Spring Boot REST API used to manage events backed by PostgreSQL, Redis, and RabbitMQ.

## Tech Stack

- **Java 21**
- **Spring Boot 3.4.5**
- **Spring Data JPA** — PostgreSQL persistence
- **Spring Data Redis** — caching
- **Spring AMQP** — RabbitMQ messaging
- **Spring Security** — authentication & authorization
- **Lombok** — boilerplate reduction
- **Maven** — build tool

## Prerequisites

- **Java 21+**
- **Docker & Docker Compose** — for infrastructure services

## Getting Started

### 1. Start Infrastructure

PostgreSQL, Redis, and RabbitMQ are defined in `docker-compose.yml`:

```bash
docker-compose up -d
```

This starts:

| Service    | Port(s)       |
|------------|---------------|
| PostgreSQL | 5432          |
| Redis      | 6379          |
| RabbitMQ   | 5672 / 15672  |

### 2. Build the Project

```bash
./mvnw compile
```

On Windows:

```powershell
.\mvnw.cmd compile
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The app starts on **http://localhost:8080**.

### 4. Authentication

Spring Security is enabled by default. On startup a password is printed in the console:

```
Using generated security password: <uuid>
```

- **Username:** `user`
- **Password:** the UUID from the console output

## Configuration

Application settings are in `src/main/resources/application.yaml`:

| Property                       | Default                                  |
|--------------------------------|------------------------------------------|
| `spring.datasource.url`       | `jdbc:postgresql://localhost:5432/taskflow` |
| `spring.datasource.username`  | `postgres`                               |
| `spring.datasource.password`  | `password`                               |
| `spring.data.redis.host`      | `localhost`                              |
| `spring.data.redis.port`      | `6379`                                   |
| `spring.rabbitmq.host`        | `localhost`                              |
| `spring.rabbitmq.port`        | `5672`                                   |

## Running Tests

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

## Project Structure

```
src/main/java/com/snow/event/manager/
├── Application.java          # Entry point
└── controller/
    └── TestController.java   # Sample REST controller
```
