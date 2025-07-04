# -------- Build stage --------
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy all source code into the container
COPY . .

# Build the project using Maven wrapper and skip tests
RUN mvn clean package -DskipTests

# -------- Runtime stage --------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy only the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]