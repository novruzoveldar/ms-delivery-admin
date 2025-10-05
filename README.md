# ms-delivery-admin

**ms-delivery-admin** is a Spring Boot microservice (admin side) that exposes administrative delivery-related endpoints and acts as a gateway to `ms-parcel-order`, `ms-courier` and `ms-courier-order` services via OpenFeign.
It provides JWT-based authentication support, token storage in Redis (Redisson), integration with Spring Cloud Config and Eureka, API documentation via Swagger, and persistence-ready configuration (JPA / PostgreSQL).

---

## Table of Contents

* [Features](#features)
* [Tech stack](#tech-stack)
* [Requirements](#requirements)
* [Quick start (local)](#quick-start-local)
* [Configuration](#configuration)
* [Available endpoints (summary & examples)](#available-endpoints-summary--examples)
* [Project structure (important packages)](#project-structure-important-packages)
* [Development notes](#development-notes)
* [Troubleshooting](#troubleshooting)
* [Contributing](#contributing)
* [License & Author](#license--author)

---

## Features

* Admin APIs for:

  * Creating parcel orders (forwards to `ms-parcel-order`)
  * Filtering and assigning couriers
  * Changing delivery state and retrieving courier order history
* Uses **OpenFeign** for inter-service communication, with a Feign interceptor that propagates `Authorization` headers.
* JWT-based authentication (`io.jsonwebtoken`), with token storage using **Redisson** (Redis).
* Spring Cloud integration: **Eureka** client (service discovery) and **Spring Cloud Config** (configuration server).
* Swagger (Springfox) enabled for API documentation.
* Persistence-ready (Spring Data JPA + PostgreSQL) and MapStruct available for mapping.

---

## Tech stack

* Java 11
* Spring Boot 2.6.x
* Spring Cloud (Eureka, Config)
* Spring Security (JWT)
* OpenFeign + feign-annotation-error-decoder
* Redisson (Redis client)
* Spring Data JPA (PostgreSQL)
* Swagger (springfox 2.9.2)
* Gradle (Gradle wrapper included)

---

## Requirements

* JDK 11
* Redis (for token cache if using `local` Redis profile)
* (Optional) PostgreSQL — if you enable DB persistence
* Spring Cloud Config server (default `http://localhost:8888`) — unless you override config
* Eureka server — if you use discovery (optional for standalone runs)
* Other microservices used by this admin service:

  * `ms-parcel-order`
  * `ms-courier`
  * `ms-courier-order`

---

## Quick start (local)

1. Build:

```bash
# Use included Gradle wrapper
./gradlew clean build
```

2. Run (examples):

```bash
# Run with Gradle (boots the app)
./gradlew bootRun

# or build jar and run
./gradlew bootJar
java -jar build/libs/ms-delivery-admin-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=local \
  --redis.url=redis://127.0.0.1:6379 \
  --application.security.authentication.jwt.secret='change-me-to-a-strong-secret' \
  --application.security.authentication.jwt.token-validity-in-seconds=86400 \
  --application.service.parcel-order.url=http://localhost:8081 \
  --application.service.courier-order.url=http://localhost:8082
```

**Notes**

* Default Spring Boot port is `8080`. Override with `--server.port=9090`.
* The project includes a `RedisConfiguration` bean that activates for profile `local` — if you want to use Redis locally, run Redis and set `--spring.profiles.active=local`.

---

## Configuration

Key configuration properties used in the code:

* `application.security.authentication.jwt.secret` — JWT signing secret (required for token parsing/verification).
* `application.security.authentication.jwt.token-validity-in-seconds` — token TTL (seconds).
* `redis.url` — Redis server URL used by Redisson (e.g., `redis://127.0.0.1:6379`).
* `application.service.parcel-order.url` — URL for `ms-parcel-order` service (Feign client).
* `application.service.courier-order.url` — URL for `ms-courier` / courier-order service (Feign client).
* `spring.cloud.config.uri` — URL of Spring Cloud Config server (default in `application.yml` is `http://localhost:8888`).

These properties can be supplied through:

* Spring Cloud Config (default configured in `application.yml`), or
* Environment variables or command-line args (`--property=value`).

---

## Available endpoints (summary & examples)

> Base endpoints are mounted under:
>
> * `/parcel` (Parcel-related)
> * `/courier/order` (Courier / order management endpoints)

### 1) Create Parcel Order

**POST** `/parcel/order`
Consumes/Produces: `application/json`
Forwards requests to the `ms-parcel-order` service (via Feign).

Example request:

```bash
curl -X POST http://localhost:8080/parcel/order \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "accountId": 123,
    "productRequestList": [
      {
        "productName": "Laptop",
        "productPrice": "1500.00",
        "productDescription": "15-inch laptop",
        "productColor": "Silver",
        "measurement": {
          "unit": "kg",
          "value": 1.5
        }
      }
    ]
  }'
```

### 2) Change Delivery State

**POST** `/courier/order/change/state`
Body: `OrderStateChangeRequest` (e.g., `{"parcelId": 1, "deliveryState": "DELIVERED"}`)

DeliveryState possible values:

```
PENDING, PROCESS, CANCELED, DELIVERED
```

### 3) Assign Courier

**POST** `/courier/order/assign/{courierId}`
Body: `OrderAssignRequest` (e.g., `{"parcelId": 1, "courierId": 10}`)

> Note: controller maps `assign/{courierId}` but the method signature takes `OrderAssignRequest` in the body — verify parameter passing when calling.

### 4) Order History (all)

**POST** `/courier/order/history/all`
Body: `CourierOrderFilter` — params: `parcelId`, `states` (list), `from`, `to`, `page`, `limit`.

### 5) Order History (paged)

**POST** `/courier/order/history`
Body: `CourierOrderFilter` — same filter structure.

### 6) Courier Filter

**POST** `/courier/order/filter`
Body: `CourierFilterRequest` — includes `availabilityState` (`READY` / `UNREADY`) and other fields.

---

## Project structure (important packages)

* `com.guavapay.config` — configuration (Feign configs, Swagger, Redis, security)
* `com.guavapay.controller` — REST controllers (`ParcelOrderController`, `AdminController`)
* `com.guavapay.integration` — Feign clients & integration configs
* `com.guavapay.model` — DTOs and request/response models
* `com.guavapay.security` — JWT utilities, `TokenProvider`, `Principal`
* `com.guavapay.cache` — token caching using Redisson
* `com.guavapay.util` — serializers / deserializers & helpers

---

## Development notes

* **Swagger**: Springfox (2.9.2) is configured — docs typically at `/v2/api-docs` and `/swagger-ui.html`.
* **Feign**: `FeignInterceptor` copies incoming `Authorization` header to outgoing Feign calls.
* **Security**: `TokenProvider` handles JWT token parsing and validation; tokens are cached in Redis by `TokenStorage`.
* **Profiles**: `local` profile enables single Redis server bean in `RedisConfiguration`.

---

## Troubleshooting

* **Config server down**: default `application.yml` points `spring.cloud.config.uri` to `http://localhost:8888`. Provide required props via CLI or env if you don't use Config Server.
* **Eureka / discovery**: if you don't run Eureka, supply concrete URLs for downstream services via `application.service.*.url`.
* **JWT errors**: set `application.security.authentication.jwt.secret` correctly.
* **Redis connectivity**: check `redis.url` and profile.
* **Feign downstream issues**: ensure downstream services are running and `application.service.*.url` values point to them.

---

## Contributing

Open issues / PRs. Follow code style, add tests, update Swagger docs when altering endpoints.

---

## License & Author

Project license: **MIT**. See `LICENSE` file.

Author: **Eldar Novruzov**

---
