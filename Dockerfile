# Stage 1: Build the Spring Boot application using Gradle
FROM --platform=linux/amd64 gradle:7.5.1-jdk17 AS build

WORKDIR /app

COPY . .

# gradlew 파일에 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle 빌드 실행
RUN ./gradlew clean build -x test

# Stage 2: Run the application using JDK
FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/build/libs/BookNote-LogServer-0.0.1-SNAPSHOT.jar /app/booknote-logserver.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/booknote-logserver.jar"]
