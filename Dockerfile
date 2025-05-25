FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Use lightweight JRE image for running the jar
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=0 /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
