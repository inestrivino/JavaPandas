# Stage 1: Build stage using Maven
FROM maven:3.8.6 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Compile the project and package it into a JAR file
RUN mvn clean package -DskipTests

# Stage 2: Final stage with minimal JDK for running the application
FROM openjdk:19-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the builder stage
COPY --from=builder /app/target/dataframe-demo-1.0-SNAPSHOT.jar /app/demo.jar

# Define the default command to run the demo
CMD ["java", "-jar", "/app/demo.jar"]