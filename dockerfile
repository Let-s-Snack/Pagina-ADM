FROM maven:eclipse-temurin AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM tomcat:10.1.28-jdk11

ENV USER=avnadmin
ENV PASSWORD=AVNS_1FznTa8oi0sxh3iE4Nm

COPY --from=build /app/target/Projeto-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Projeto.war

EXPOSE 8080
