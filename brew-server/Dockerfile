ARG BUILD_VERSION=0.0.1
FROM gradle:6.3-jdk11 as BUILD
ADD src src
ADD build.gradle .
ADD gradle.properties .

RUN gradle build -x test

FROM amazoncorretto:11-alpine-jdk as BUILDER
ARG BUILD_VERSION
WORKDIR application
COPY --from=BUILD /home/gradle/build/libs/gradle-${BUILD_VERSION}.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM amazoncorretto:11-alpine-jdk
WORKDIR application
EXPOSE 8080
COPY --from=BUILDER application/dependencies/ ./
COPY --from=BUILDER application/spring-boot-loader/ ./
COPY --from=BUILDER application/snapshot-dependencies/ ./
COPY --from=BUILDER application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
