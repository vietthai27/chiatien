# Stage 1: Build the JAR
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /build
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
