# DataGithub microservice

Service built to serve different data from Github.

## Index

- [Getting Started](#getting-started)
- [Tech stacks](#tech-stacks)
- [Architecture](#architecture)
- [Infrastructure](#infrastructure)
- [Troubleshoting](#troubleshoting)

## Getting Started

How to run the project:

1. Check the `.launchers` folder to see the different launchers.
2. Run Application.run.xml to start the application. (This will start the Springboot application)

## Tech stacks

- Java 22
- Springboot 3
- Spring Data JDBC
  - Way to interact with the database without the need of an ORM. Performance is better than JPA Hibernate.
- Spring Data JPA Hibernate
  - Way to interact with the database using an ORM (Hibernate) with all the entities and relationships configured.
- Spring Batch
  - Batch processing. (Here is used as cleanup job scheduled to remove old data from the database with Tasklet).
- Spring Actuator
  - You can check the health of the application in the following URL: `http://localhost:8080/actuator` or just `http://localhost:8080/actuator/health`
- Springboot Webflux
  - Reactive programming. Async API to perform non-blocking operations in the main thread application.
  - Used also to consume the Github API asynchrously (instead Java 11 HttpClient or OkHttp).
- Spring Config
  - Centralized configuration.
- Spring Data Redis (instead of Caffeine)
  - To cache data.
- SpringBoot Config Processor
  - To generate the configuration file. (Its a helper to generate the configuration file).
- SpringBoot DevTools
  - To reload the application when a change is detected.

#### DDBB:

- H2

#### Streaming server:

- Kafka
  - To stream data between microservices (Producer/Consumer).

### CI/CD

- Check the section below `Infrastructure`.

## Architecture

- Microservices.
  - There are (actually) 2 microservices connected to the same Kafka broker.
    - (This) Java microservice (DataGithub).
    - Python & FastAPI microservice (Monitorization).
- Hexagonal. 

Check the `server` folder.

## Infrastructure

- Docker
- Github Actions

## Troubleshoting


---

Shield: [![CC-BY-NC-ND 4.0][CC-BY-NC-ND-shield]][CC-BY-NC-ND]

This work is licensed under a [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.][CC-BY-NC-ND]

[![CC-BY-NC-ND 4.0][CC-BY-NC-ND-image]][CC-BY-NC-ND]

[CC-BY-NC-ND-shield]: https://img.shields.io/badge/License-CC--BY--NC--ND--4.0-lightgrey
[CC-BY-NC-ND]: http://creativecommons.org/licenses/by-nc-nd/4.0/
[CC-BY-NC-ND-image]: https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png
