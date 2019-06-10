FROM gradle:4.3.0-jdk8-alpine as BUILD

ADD src src
ADD build.gradle .
ADD gradle.properties .

RUN gradle build -x test

FROM openjdk:8-jdk-alpine
ARG BUILD_VERSION
EXPOSE 8080 8080
COPY --from=BUILD /home/gradle/build/libs/gradle-${BUILD_VERSION}.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar