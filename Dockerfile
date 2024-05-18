# Use a base image containing Java runtime environment
FROM openjdk:17-jdk-slim

LABEL authors="ahmetozmus"

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/CommunityApplication-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which the Spring Boot app runs
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]
