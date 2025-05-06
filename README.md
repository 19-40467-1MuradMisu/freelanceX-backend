# 🧑‍💻 FreelanceX Platform

**FreelanceX** is a microservices-based platform built to simulate a freelance job marketplace. It leverages Spring Boot, Docker, Kafka, and PostgreSQL for a fully containerized, event-driven architecture.

## 🚀 Features

- Service Discovery with **Eureka**
- API Gateway for routing
- Kafka for asynchronous communication
- PostgreSQL per service
- Dockerized deployment
- Health checks for reliability

---

## 🏁 Getting Started

### 📦 Prerequisites

- Docker & Docker Compose installed
- Java 17+ (for development)
- Git

### ▶️ Start All Services

```bash
docker-compose up --build
```

---

## ✅ Verify Services

| Service       | URL                           |
| ------------- | ----------------------------- |
| Eureka Server | http://localhost:8761         |
| API Gateway   | http://localhost:8080         |
| Kafka Broker  | PLAINTEXT at `localhost:9092` |
| PostgreSQL    | Default port `5432`           |

---

## 📂 Project Structure

```
freelancex-platform/
├── ApiGateway/
├── BiddingService/
├── JobService/
├── NotificationService/
├── PaymentService/
├── RatingService/
├── ServiceDiscovery/
├── SkillService/
├── UserService/
├── docker-compose.yml
├── init-db.sh
└── README.md
```

---

## 🧪 Databases

The `init-db.sh` script runs automatically and creates the following PostgreSQL databases:

- `user_service`
- `job_service`
- `bidding_service`
- `payment_service`
- `notification_service`
- `rating_service`
- `skill_service`
- `gateway_service`

---

## 🗺️ Networking

Docker Compose defines two networks for isolation and communication:

- `freelanceX-network`: Shared by all services
- `kafka-network`: For Kafka and Zookeeper only

---

## 🔐 Environment Configuration

All critical variables such as:

- PostgreSQL credentials
- Kafka configuration
- Service ports

are defined inside `docker-compose.yml`.

---

## 📬 Event-Driven Communication

Kafka facilitates asynchronous communication between services:

- **Bidding events**
- **Payment status updates**
- **Job notifications**

---

## 🧠 Health Checks

Docker health checks are set up for:

- `Zookeeper`
- `Kafka Broker`
- `PostgreSQL`

This ensures services wait for dependencies to be healthy before starting.

---

## 📄 License

This project is licensed under the **TARTU UNIVERSITY License**.

---

## 🤝 Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Apache Kafka](https://kafka.apache.org/)
- [Docker](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org/)
