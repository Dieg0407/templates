FROM eclipse-temurin:17.0.1_12-jdk-alpine as runtime

WORKDIR /opt/app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]