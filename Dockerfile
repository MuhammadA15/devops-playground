FROM openjdk:21-jdk

WORKDIR /app

ARG APP_VERSION

COPY target/*.jar /app/

EXPOSE 8080

ENV JAR_VERSION=${APP_VERSION}

CMD java -jar demo-${JAR_VERSION}-SNAPSHOT.jar