# Mahabharata Gods

REST API to Calculate the popularity of Indian gods in Mahabharata book. 
The goal of the project/challenge is to explore feature of **CompletableFutures** in java.


## Pre-requisites

- Java 11
- Maven

## Build

```bash
./mvnw clean install
```

## Run

```bash
./mvnw spring-boot:run
```

## Docker

```bash
docker build -t mahabharata-gods .
docker run -p8080:8080 mahabharata-gods:latest
```
