FROM openjdk:8-jdk-alpine
MAINTAINER code4copy.com
COPY build/libs/redirect-service-1.0-SNAPSHOT.jar redirect-service.jar
ENTRYPOINT ["java","-jar","/redirect-service.jar"]