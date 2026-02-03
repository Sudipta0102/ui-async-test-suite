# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy Maven descriptor first (enables dependency caching)
COPY pom.xml .

# Download dependencies (cached layer)
RUN mvn -B -q dependency:go-offline

# Copy source code
COPY src ./src

# Build executable jar (skip tests for image build)
RUN mvn -B clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-jammy

# Create non-root user for security
RUN useradd -ms /bin/bash appuser

# Set working directory
WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose backend port
EXPOSE 8080

# Switch to non-root user
USER appuser

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
