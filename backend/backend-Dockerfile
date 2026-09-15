# --- Stage 1: Build the JAR ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first for better layer caching (dependencies won't re-download on code-only changes)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the actual source and build
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Slim runtime image ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy only the built JAR from the build stage — keeps final image small
COPY --from=build /app/target/*.jar app.jar

# Render sets PORT dynamically — Spring Boot needs to listen on it
ENV PORT=8080
EXPOSE 8080

# Cap JVM heap so it stays well within Render's free tier 512MB limit
CMD ["sh", "-c", "java -Xmx400m -Dserver.port=${PORT} -jar app.jar"]
