#TODO: MAKE A PROJECT DEMO

# We use an official OpenJDK runtime as a parent image
FROM openjdk:19-jdk-slim

# Working directory
WORKDIR /usr/src/demo

# We copy the application's JAR file to the container TODO
# COPY COPY target/javapandas-1.0-SNAPSHOT.jar demo.jar .

# Command to run the demo when the container starts TODO
# CMD ["java", "-jar", "JavaPandas.jar"]