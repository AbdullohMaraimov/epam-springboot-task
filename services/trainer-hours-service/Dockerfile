FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/trainer-hours-service-0.0.1-SNAPSHOT.jar workload.jar

EXPOSE 8060

ENTRYPOINT ["java", "-jar", "workload.jar"]