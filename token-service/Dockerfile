FROM openjdk:8-jdk-alpine
MAINTAINER code4copy.com
COPY build/libs/token-service-1.0-SNAPSHOT.jar token-service.jar
ENTRYPOINT ["java","-jar","/token-service.jar"]