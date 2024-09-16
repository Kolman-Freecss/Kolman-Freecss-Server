# Stage 1: Build stage
FROM maven:3.9.4-eclipse-temurin-22 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies (this step will be cached unless pom.xml changes)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the project using Maven
RUN mvn clean package -DskipTests

# Stage 2: Run stage
FROM eclipse-temurin:22-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
