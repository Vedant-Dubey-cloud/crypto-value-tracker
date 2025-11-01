# Use OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper and pom.xml first for dependency caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# ✅ Give executable permission to mvnw (fixes "Permission denied")
RUN chmod +x mvnw

# Copy the rest of your project files
COPY . .

# Build the Spring Boot JAR (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Expose the port your app runs on
EXPOSE 9090

# Run the Spring Boot application
CMD ["java", "-jar", "target/crypto-0.0.1-SNAPSHOT.jar"]
