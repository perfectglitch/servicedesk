################################  BUILD  ##################################

FROM gradle:jdk8 as build

WORKDIR /home/gradle/project/

COPY src src
COPY build.gradle settings.gradle ./

RUN gradle test build

################################   RUN   ##################################

FROM openjdk:8-jdk-alpine

COPY --from=build /home/gradle/project/build/libs/servicedesk-*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
