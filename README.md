# Simple Service Desk App

## About

Java test assignment implementation using Gradle, Spring Boot and React.

### Requirements

Java 8

## Quick usage

To  build, test and package executable .jar use custom Gradle task `stage`.

> gradle stage

Start application by running:

> java -jar build/libs/servicedesk-0.0.1-SNAPSHOT.jar

After application has started open `http://localhost:8080` in your browser to use app.

### Populate DB with sample records

Set `spring.profiles.active` Java property value to `demo` in order to start application with some sample data.

> java -Dspring.profiles.active=demo -jar build/libs/servicedesk-0.0.1-SNAPSHOT.jar
