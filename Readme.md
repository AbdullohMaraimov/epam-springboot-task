# Microservices Application
* This application is CRM for GYMs

A microservices-based application built with Spring Boot, Docker, H2(in memory), PostgreSQL and MongoDB.

## Services Overview

- **Discovery Service**: Eureka service registry (`http://localhost:8761`)
- **Gateway**: API Gateway for routing (`http://localhost:8222`)
- **Auth Service**: Authentication APIs with H2 (`http://localhost:8080`)
- **Workload Service**: Trainer workload management with MongoDB (`http://localhost:8060`)
- **CRM Service**: Customer management with PostgreSQL (`http://localhost:8090`)
- **Postgres**: Database for CRM Service
- **PgAdmin**: PostgreSQL management tool (`http://localhost:5050`)

## Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/)

## Getting Started

1. Clone the repository:
   ```bash
   git clone git@github.com:AbdullohMaraimov/epam-springboot-task.git

2. Run the docker compose:
   ```bash
   docker-compose up -d --build

## Passwords for Databases

* crm-service database(Postgresql): user = postgres; password = postgres

* auth-service database(H2): user = sa; password = password

* workload-service database(MongoDB): user = admin; password = admin
   