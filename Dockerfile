FROM openjdk:26-ea-trixie

ARG JAR_FILE=build/libs/*.jar

# Copy your Spring Boot app
COPY ${JAR_FILE} auth-service.jar

# Download OpenTelemetry Java agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.30.0/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

# Expose the service port
EXPOSE 8050

# OTEL environment variables for HTTP OTLP
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector:4318
ENV OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf
ENV OTEL_SERVICE_NAME=auth-service
ENV OTEL_TRACES_EXPORTER=otlp
ENV OTEL_METRICS_EXPORTER=otlp
ENV OTEL_LOGS_EXPORTER=otlp
ENV OTEL_RESOURCE_ATTRIBUTES=deployment.environment=dev

# Run Spring Boot with OTEL agent
ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "auth-service.jar"]
