FROM openjdk:17
WORKDIR /application
COPY microkafka-notification/target/microkafka-notification-1.0.jar app.jar
EXPOSE 9000
CMD java -jar -Dspring.profiles.active=docker app.jar