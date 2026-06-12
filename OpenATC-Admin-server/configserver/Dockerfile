FROM openjdk:8-jre-alpine
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

ADD ConfigServer.jar /config.jar
ENTRYPOINT ["java","-jar","./config.jar"]
