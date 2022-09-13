FROM openjdk:11

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/server-microservice"

COPY ${JAR_FILE}  /opt/server-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/server-microservice/app.jar"]