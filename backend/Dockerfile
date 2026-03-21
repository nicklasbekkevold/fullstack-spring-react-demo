FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY ./pom.xml /app
COPY ./src /app/src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
