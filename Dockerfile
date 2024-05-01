FROM openjdk:21-jdk

WORKDIR /app

ARG APP_VERSION

COPY target/demo-*-SNAPSHOT.jar /app/

EXPOSE 8080

ENV JAR_VERSION=${APP_VERSION}

CMD java -jar demo-${APP_VERSION}.jar