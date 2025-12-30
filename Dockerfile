#FROM openjdk:17-jdk-alpine
FROM openjdk:26-ea-trixie
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} auth-service.jar
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/auth-service.jar"]