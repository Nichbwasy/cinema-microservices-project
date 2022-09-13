FROM openjdk:11

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/films-microservice"

COPY ${JAR_FILE}  /opt/films-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/films-microservice/app.jar"]