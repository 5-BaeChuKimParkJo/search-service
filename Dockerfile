FROM eclipse-temurin:17-jdk-alpine

# JAR 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 포트 오픈
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "/app.jar"]