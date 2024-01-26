FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/*.jar books.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/books.jar"]