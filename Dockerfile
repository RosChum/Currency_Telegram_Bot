FROM openjdk:17-oracle
LABEL authors="Rost"

WORKDIR /app

COPY build/libs/cryptoBot-0.0.1-SNAPSHOT.jar cryptoBot.jar
EXPOSE 8080
CMD ["java", "-jar", "cryptoBot.jar"]