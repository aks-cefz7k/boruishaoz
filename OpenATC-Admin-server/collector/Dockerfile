FROM openjdk:8-jre-alpine
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

ADD collector-1.0.0.jar /app.jar
ENTRYPOINT ["java","-jar","./app.jar","--spring.profiles.active=docker"]



