FROM openjdk:8-jdk-alpine

VOLUME /tmp

RUN mkdir /app

ADD ./build/libs/brewlog-**.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
