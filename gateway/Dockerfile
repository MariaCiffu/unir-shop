FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/*.jar gateway.jar
EXPOSE 9191
ENTRYPOINT ["java","-jar","/gateway.jar"]