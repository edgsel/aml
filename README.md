# LHV AML API v1

A small core banking solution to create, get accounts and transactions.

## Required setup (at the moment of development)
JDK 17+

Gradle 8.3+

Docker 4.25.0+

## Required ENV variables:

`POSTGRES_DB=lhv_aml`

`POSTGRES_DEFAULT_SCHEMA=public`

`POSTGRES_USERNAME=<your_db_username>`

`POSTGRES_PASSWORD=<your_db_password>`


# To run application locally

## 1. Set and export ENV variables (Linux/MacOS)
* `export $(xargs < .env)`

## 2. Start up DB and RabbitMQ containers
* `docker-compose up -d postgresdb`

## 3. To run the project with Gradle for `MacOS/Linux`:
Build the project (NB! Make sure that env variables are set and exported and DB is up and running as liquibase migrations are run on ./gradlew build):
* `./gradlew clean build` 

Run tests:
* `./gradlew test`

## 5. And then...
Run the project:
* `./gradlew bootRun`


# API docs
* for Tuum API docs go to `http://localhost:8009/docs/swagger-ui/index.html`


## What can be improved
* API docs (more detailed info about DTOs' fields)
* More tests (acceptance, unit, integration)
* Improved name matching logic
