FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar

EXPOSE 8222

ENTRYPOINT ["java", "-jar", "gateway.jar"]