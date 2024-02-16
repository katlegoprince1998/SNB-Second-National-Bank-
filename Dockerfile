# Use OpenJDK 21 as the base image
FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

# Add the packaged Spring Boot application JAR file to the container
ADD target/*.jar SNB-Second-National-Bank-0.0.1-SNAPSHOT.jar

# Specify the entry point for the container
ENTRYPOINT ["java", "-jar", "SNB-Second-National-Bank-0.0.1-SNAPSHOT.jar"]
