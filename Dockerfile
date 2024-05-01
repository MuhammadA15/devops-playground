FROM openjdk:21-jdk

WORKDIR /app

COPY target/demo-0.0.1-SNAPSHOT.jar /app/devops-playground.jar

EXPOSE 8080

CMD [ "java", "-jar", "devops-playground.jar" ]