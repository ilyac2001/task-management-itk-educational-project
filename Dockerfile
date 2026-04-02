FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/itk-test-task-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]