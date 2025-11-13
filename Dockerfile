# --- STAGE 1: BUILD ---
FROM maven:3.9.5-openjdk-17 AS build
WORKDIR /app
# Copy the project files (pom.xml first for dependency caching)
COPY pom.xml .
COPY src /app/src
# Build the JAR file
RUN mvn clean package -DskipTests

# --- STAGE 2: RUNTIME ---
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
# Copy only the compiled JAR from the 'build' stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]