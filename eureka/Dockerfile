FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar books.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/books.jar"]