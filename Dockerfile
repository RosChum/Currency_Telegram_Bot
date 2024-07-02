FROM openjdk:17-oracle as build
LABEL authors="Rost"


## Используем официальный образ OpenJDK для базового слоя
#FROM openjdk:11-jdk-slim as build

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем gradle файлы в контейнер
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Копируем исходный код проекта
COPY src src

# Выдаем права на выполнение для gradlew и собираем проект
#RUN chmod +x ./gradlew && ./gradlew build -x test
RUN #apt-get update && apt-get install -y dos2unix
RUN #dos2unix gradlew
RUN chmod +x ./gradlew


# Копируем собранный JAR файл из build/libs в текущую директорию
RUN cp build/libs/cryptoBot-0.0.1-SNAPSHOT.jar cryptoBot.jar

# Определяем новый базовый слой для уменьшения размера конечного образа
FROM openjdk:17-oracle

# Копируем JAR файл из предыдущего слоя
COPY --from=build app/cryptoBot.jar .

# Открываем порт, который будет использоваться приложением
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java","-jar","/app.jar"]




#WORKDIR /app
#
#COPY build/libs/cryptoBot-0.0.1-SNAPSHOT.jar cryptoBot.jar
#EXPOSE 8080
#CMD ["java", "-jar", "cryptoBot.jar"]