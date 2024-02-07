FROM openjdk:17

# JAR 파일 복사
ARG JAR_FILE=build/libs/ViewCareFull-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Swagger UI 파일과 OpenAPI 스펙 파일 복사
COPY build/resources/main/static /app/static
COPY build/api-spec /app/api-spec

USER root
# ffmpeg 설치를 위한 명령어
# 패키지 목록 업데이트 및 필수 패키지 설치
RUN apt-get update \
    && apt-get install -y ffmpeg \
    # 의존성이 있는 다른 패키지들을 설치하지 않도록 권장하는 옵션을 추가할 수 있습니다.
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Java 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app.jar"]

# 포트 8080 노출
EXPOSE 8080