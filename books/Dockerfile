FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar
COPY src/main/resources/public.pem /app/public.pem

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "app.jar"]