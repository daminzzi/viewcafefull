FROM openjdk:17-slim

# JAR 파일 복사
ARG JAR_FILE=build/libs/ViewCareFull-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Swagger UI 파일과 OpenAPI 스펙 파일 복사
COPY build/resources/main/static /app/static
COPY build/api-spec /app/api-spec

RUN apt-get update && apt-get install -y \
    ffmpeg \
    && rm -rf /var/lib/apt/lists/*

# Java 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app.jar"]

# 포트 8080 노출
EXPOSE 8080