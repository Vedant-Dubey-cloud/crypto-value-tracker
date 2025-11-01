# Use OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy all project files
COPY . .

# ✅ Ensure mvnw has executable permission AFTER all copies
RUN chmod +x ./mvnw

# Build the Spring Boot JAR (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Expose the port your app runs on
EXPOSE 9090

# Run the Spring Boot application
CMD ["java", "-jar", "target/crypto-0.0.1-SNAPSHOT.jar"]
