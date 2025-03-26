#TODO: MAKE A PROJECT DEMO

# We use an official OpenJDK runtime as a parent image
FROM openjdk:19-jdk-slim

# Working directory
WORKDIR /usr/src/app

# We copy the application's JAR file to the container
COPY target/JavaPandas.jar .

# Command to run the demo when the container starts
CMD ["java", "-jar", "JavaPandas.jar"]