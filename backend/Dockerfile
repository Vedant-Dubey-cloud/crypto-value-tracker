# Use a lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything to container
COPY . .

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Expose the port Spring Boot runs on
EXPOSE 9090

# Run the built jar
CMD ["java", "-jar", "target/crypto-0.0.1-SNAPSHOT.jar"]
