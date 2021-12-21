FROM eclipse-temurin:11.0.12_7-jre as RUNTIME

WORKDIR /opt/api

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]