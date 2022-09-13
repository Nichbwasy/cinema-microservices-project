FROM openjdk:11

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/cinemas-microservice"

COPY ${JAR_FILE}  /opt/cinemas-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/cinemas-microservice/app.jar"]