FROM openjdk:11

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/authorization-microservice"

COPY ${JAR_FILE}  /opt/authorization-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/authorization-microservice/app.jar"]