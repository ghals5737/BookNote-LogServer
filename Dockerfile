# Stage 1: Build the Spring Boot application using Gradle
FROM --platform=linux/amd64 gradle:7.5.1-jdk18 AS build

ARG MONGODB_URL
ARG MONGODB_PORT
ARG KAFKA_HOST

WORKDIR /app/booknote
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Run the application using JDK
FROM openjdk:18-jdk-alpine

WORKDIR /app
COPY --from=build /app/build/libs/BookNote-LogServer-0.0.1-SNAPSHOT.jar /app/booknote-logserver.jar

# These environment variables are required during runtime
#ENV DB_URL=${DB_URL}
#ENV DB_USER_NAME=${DB_USER_NAME}
#ENV DB_PASS_WORD=${DB_PASS_WORD}
#ENV REDIS_HOST=${REDIS_HOST}
#ENV REDIS_PORT=${REDIS_PORT}
#ENV KAFKA_HOST=${KAFKA_HOST}
#ENV NAVER_CLIENT_ID=${NAVER_CLIENT_ID}
#ENV NAVER_CLIENT_SECRET=${NAVER_CLIENT_SECRET}

EXPOSE 8080
CMD ["java", "-jar", "/app/booknote-logserver.jar"]
