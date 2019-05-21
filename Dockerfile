FROM maven:3.6.1-jdk-8-alpine AS build-env

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM tomcat:9.0.20-jre8

COPY --from=build-env /app/target/joboonja.war $CATALINA_HOME/webapps/joboonja.war