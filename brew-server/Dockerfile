ARG BUILD_VERSION=0.0.1
FROM gradle:7.6-jdk17 as BUILD
WORKDIR /usr/app/
ADD src src
ADD build.gradle .
ADD gradle.properties .
RUN gradle build -x test

FROM amazoncorretto:17-alpine-jdk as BUILDER
ARG BUILD_VERSION
ENV APP_HOME=/usr/app
WORKDIR application
COPY --from=BUILD ${APP_HOME}/build/libs/app-${BUILD_VERSION}-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM amazoncorretto:17-alpine-jdk
WORKDIR application
EXPOSE 8080
COPY --from=BUILDER application/dependencies/ ./
COPY --from=BUILDER application/spring-boot-loader/ ./
COPY --from=BUILDER application/snapshot-dependencies/ ./
COPY --from=BUILDER application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
