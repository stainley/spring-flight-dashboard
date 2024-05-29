# **Spring Boot Flight Dashboard**

## Overview

The Spring Boot Flight Dashboard is a full-stack application that leverages Spring Boot, Apache Kafka, WebSocket, and
ReactJS to create a real-time flight dashboard. This project consists of multiple components:

* react-flight-dashboard: Frontend application built with ReactJS.
* spring-flight-consumer: Spring Boot application that consumes flight data.
* spring-flight-producer: Spring Boot application that produces flight data.
* spring-flight-shared: Shared library used by both producer and consumer.
* docker-compose.yml: Docker Compose configuration to set up the necessary infrastructure, including Kafka and
  Zookeeper.

## Prerequisites

* Docker and Docker Compose
* Java 21
* Maven
* Node.js and npm (for the React frontend)

## Installation and Runtime

### 1. Clone the Repository

```git
    git clone https://github.com/yourusername/spring-boot-flight-dashboard.git
    cd spring-boot-flight-dashboard
```

### 2. Build the Shared Library

Navigate to the spring-flight-shared directory and build the shared library:

### 3. Build the Producer and Consumer Applications

Navigate to the root directory of both the spring-flight-consumer and spring-flight-producer and build them:

<hr />

```bash
    cd spring-flight-consumer
```

```mvn
    mvn clean package
```

```bash
    cd ../spring-flight-producer
```    

```mvn
    mvn clean package    
    mvn clean package
```
<hr />

### 4. Set Up Docker Environment

Ensure you are in the root directory where the docker-compose.yml file is located, then run:

```bash
    docker-compose up -d
```
This command will start Zookeeper and Kafka containers.

```dockerfile

version: "3.9"
services:
  zookeeper:
    image: wurstmeister/zookeeper:latest
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka:latest
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    depends_on:
      - zookeeper
```

### 5. Run the Producer and Consumer Applications
Navigate to the directories of both the producer and consumer and run them:

### 6. Run the React Frontend
Navigate to the react-flight-dashboard directory, install dependencies, and start the development server:

```bash
  cd react-flight-dashboard
  npm install
  vite --host
```
The React application will be available at http://localhost:5173/.


## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.
