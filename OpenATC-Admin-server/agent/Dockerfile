#FROM 192.168.13.110:5080/openjdk:8-jre-alpine
FROM openjdk:8-jre-alpine

ARG JAR_FILE
ENTRYPOINT ["java","-jar","./app.jar","--spring.profiles.active=docker"]

COPY target/${JAR_FILE} ./app.jar
#COPY token/token.txt ./token/token.txt


