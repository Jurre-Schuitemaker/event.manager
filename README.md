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

The API uses **JWT-based stateless authentication**. Public endpoints live under `/auth/**`.

#### Register a user

```http
POST /auth/register/user
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "secret123"
}
```

#### Register an organizer

```http
POST /auth/register/organizer
```

#### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "secret123"
}
```

All three endpoints return a JWT token:

```json
{
  "token": "eyJhbGciOi...",
  "email": "john@example.com",
  "role": "USER"
}
```

Include the token in subsequent requests:

```
Authorization: Bearer <token>
```

#### Role-based access

| Path              | Allowed roles          |
|-------------------|------------------------|
| `/auth/**`        | Public (no token)      |
| `/admin/**`       | ADMIN                  |
| `/organizer/**`   | ORGANIZER, ADMIN       |
| All other paths   | Any authenticated user |

## API Endpoints

### Events

#### Create an event

Creating an event automatically generates seats numbered 1 through `totalSeats`.

```http
POST /events
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Spring Conference",
  "description": "Annual Spring Boot conference",
  "location": "Amsterdam",
  "date": "2026-09-15T10:00:00",
  "totalSeats": 100,
  "price": 49.99
}
```

#### Get an event

```http
GET /events/{id}
Authorization: Bearer <token>
```

#### Update an event

```http
PUT /events/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Updated Title",
  "description": "Updated description",
  "location": "Rotterdam",
  "date": "2026-10-01T14:00:00",
  "totalSeats": 150,
  "price": 59.99
}
```

#### Delete an event

Only the event organizer or an admin can delete an event. Deletes all associated seats.

```http
DELETE /events/{id}
Authorization: Bearer <token>
```

### Users

#### Get a user

```http
GET /users/{id}
Authorization: Bearer <token>
```

#### Update a user

```http
PUT /users/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@example.com"
}
```

#### Delete a user

Only the user themselves or an admin can delete a user.

```http
DELETE /users/{id}
Authorization: Bearer <token>
```

### Seats

Seats are nested under events.

#### List all seats for an event

```http
GET /events/{eventId}/seats
Authorization: Bearer <token>
```

#### List available seats for an event

```http
GET /events/{eventId}/seats/available
Authorization: Bearer <token>
```

#### Get a specific seat

```http
GET /events/{eventId}/seats/{seatId}
Authorization: Bearer <token>
```

## Configuration

Application settings are in `src/main/resources/application.yaml`:

| Property                       | Default                                    |
|--------------------------------|--------------------------------------------|
| `spring.datasource.url`       | `jdbc:postgresql://localhost:5432/taskflow` |
| `spring.datasource.username`  | `postgres`                                  |
| `spring.datasource.password`  | `password`                                  |
| `spring.data.redis.host`      | `localhost`                                 |
| `spring.data.redis.port`      | `6379`                                      |
| `spring.rabbitmq.host`        | `localhost`                                 |
| `spring.rabbitmq.port`        | `5672`                                      |

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
├── Application.java
├── auth/
│   ├── controller/AuthController.java
│   ├── dto/
│   │   ├── AuthResponse.java
│   │   ├── LoginRequest.java
│   │   └── RegisterRequest.java
│   ├── jwt/
│   │   ├── JwtAuthenticationEntryPoint.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtService.java
│   └── service/AuthService.java
├── booking/
│   └── entity/
│       ├── Booking.java
│       └── BookingStatus.java
├── config/
│   └── SecurityConfig.java
├── event/
│   ├── controller/EventController.java
│   ├── dto/
│   │   ├── CreateEventRequest.java
│   │   ├── EventResponse.java
│   │   └── UpdateEventRequest.java
│   ├── entity/Event.java
│   ├── mapper/EventMapper.java
│   ├── repository/EventRepository.java
│   └── service/EventService.java
├── payment/
│   └── entity/Payment.java
├── seat/
│   ├── controller/SeatController.java
│   ├── dto/SeatResponse.java
│   ├── entity/
│   │   ├── Seat.java
│   │   └── SeatStatus.java
│   ├── mapper/SeatMapper.java
│   ├── repository/SeatRepository.java
│   └── service/SeatService.java
├── security/
│   └── permissions/Role.java
└── user/
    ├── controller/UserController.java
    ├── dto/
    │   ├── AssignRoleRequest.java
    │   ├── CreateUserRequest.java
    │   ├── UpdateUserRequest.java
    │   └── UserResponse.java
    ├── entity/User.java
    ├── mapper/UserMapper.java
    ├── repository/UserRepository.java
    └── service/UserService.java
```
