FROM openjdk:17
WORKDIR /app
COPY microkafka-payment/target/microkafka-payment-1.0.jar app.jar
EXPOSE 9090
CMD java -jar -Dspring.profiles.active=docker app.jar