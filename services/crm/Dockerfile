FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/crm-0.0.1-SNAPSHOT.jar crm.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "crm.jar"]