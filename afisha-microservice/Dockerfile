FROM openjdk:11

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/afisha-microservice"

COPY ${JAR_FILE} opt/afisha-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/afisha-microservice/app.jar"]