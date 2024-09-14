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
- Spring Data JPA Hibernate
- Spring Batch
- Spring Actuator
- Springboot Webflux
- Spring Config

#### DDBB:

- H2

#### Streaming server:

- Kafka

### CI/CD

- Check the section below `Infrastructure`.

## Architecture

- Microservices.
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
