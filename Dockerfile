FROM openjdk:17-oracle
LABEL authors="Rost"

WORKDIR /app

COPY build/libs/cryptoBot-0.0.1-SNAPSHOT.jar cryptoBot.jar

ENV SOCKET_SERVER_HOST=localhost
ENV SOCKET_SERVER_PORT=8080


CMD ["java", "-jar", "booking-app.jar"]