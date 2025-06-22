FROM openjdk:21-jdk-slim

LABEL maintainer="Julio"
LABEL description="A Docker image for running a Java application with a specific JAR file."

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o JAR necessário para dentro do container
COPY target/*.jar app.jar

# Remove caches do apt (caso use apt-get futuramente)
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
