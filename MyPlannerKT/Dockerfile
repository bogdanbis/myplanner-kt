# Multi-stage build for MyPlannerKT

# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy gradle files first for better layer caching
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies (will be cached if no changes)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copy the configutation file
COPY src/main/resources/application-prod.yaml ./application.yaml


# Expose the application port
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
