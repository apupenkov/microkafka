FROM maven:3.8.1-openjdk-17-slim as builder
#COPY ./.mvn/ .mvn
#COPY ./mvnw ./microkafka-integration-tests/pom.xml ./
#RUN chmod +x mvnw
#RUN ./mvnw dependency:go-offline
#COPY ./microkafka-integration-tests ./microkafka-integration-tests
#CMD ./mvnw test

COPY ./.mvn/ .mvn
COPY ./microkafka-integration-tests ./
COPY ./mvnw /mvnw
#COPY ./microkafka-integration-tests/pom.xml ./
RUN mvn dependency:go-offline
#COPY ./microkafka-integration-tests/src ./src
#RUN mvn -f /app/pom.xml clean package
#RUN mvn package -DskipTests
#FROM openjdk:17
#COPY --from=build /app/target/microkafka-integration-tests-1.0.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]
CMD mvn test