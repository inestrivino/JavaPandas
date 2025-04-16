# Use an official Maven image as a parent image
FROM maven:3.8.6-openjdk-19-slim

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Compile the project
RUN mvn package

# Use an official OpenJDK runtime as a parent image for the final stage
FROM openjdk:19-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the previous stage
COPY --from=0 /app/target/file.jar .

# Définir la commande par défaut pour exécuter le programme
CMD ["java", "-jar", "file.jar"]
