ARG BUILD_VERSION=0.0.1
FROM gradle:6.3-jdk8 as BUILD
ADD src src
ADD build.gradle .
ADD gradle.properties .

RUN gradle build -x test

FROM openjdk:8-jdk-alpine as BUILDER
ARG BUILD_VERSION
WORKDIR application
COPY --from=BUILD /home/gradle/build/libs/gradle-$BUILD_VERSION-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:8-jdk-alpine
WORKDIR application
EXPOSE 8080
COPY --from=BUILDER application/dependencies/ ./
COPY --from=BUILDER application/spring-boot-loader/ ./
COPY --from=BUILDER application/snapshot-dependencies/ ./
COPY --from=BUILDER application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
