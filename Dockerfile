FROM openjdk:21-jdk-slim

WORKDIR /app

# Download OpenTelemetry Java agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar .

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", \
    "-javaagent:/app/opentelemetry-javaagent.jar", \
    "-Dotel.service.name=balneabilidade-api", \
    "-Dotel.exporter.otlp.endpoint=http://otel:4317", \
    "-Dotel.metrics.exporter=otlp", \
    "-Dotel.logs.exporter=otlp", \
    "-Dotel.traces.exporter=otlp", \
    "-jar", "app.jar"]