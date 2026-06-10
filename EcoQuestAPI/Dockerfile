# ── Stage 1: Build ────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Descargar dependencias primero (cacheo de capas)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Compilar el proyecto
COPY src ./src
RUN mvn package -DskipTests -q

# ── Stage 2: Run ──────────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/target/aplicacion-0.0.1-SNAPSHOT.war app.war

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.war"]
