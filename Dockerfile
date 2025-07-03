# Multi-stage build for MyPlannerKT

# Node.js build stage for MyPlannerWeb
FROM node:latest AS web-build
WORKDIR /app

# Copy package.json and package-lock.json
COPY MyPlannerWeb/package*.json ./

# Install dependencies
RUN npm ci

# Copy the rest of the web application
COPY MyPlannerWeb/ ./

# Build the web application
RUN npm run build

# Java build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy gradle files first for better layer caching
COPY MyPlannerKT/gradlew .
COPY MyPlannerKT/gradle ./gradle
COPY MyPlannerKT/build.gradle.kts .
COPY MyPlannerKT/settings.gradle.kts .

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies (will be cached if no changes)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY MyPlannerKT/src ./src

# Copy the built web application from the web-build stage
COPY --from=web-build /app/dist ./src/main/resources/static

# Build the application
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copy the configutation file
COPY MyPlannerKT/src/main/resources/application-prod.yaml ./application.yaml


# Expose the application port
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
