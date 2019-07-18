# Simple Service Desk App

## About

Java Service Desk sample implementation using Spring Data REST and React.

### Requirements

Java 8

## Usage

Build application:

> gradlew build

Start application by running:

> java -jar build/libs/servicedesk-0.0.1-SNAPSHOT.jar

After application has started open `http://localhost:8080` in your browser.

![dashboard_PNG](https://github.com/perfectglitch/servicedesk/raw/master/servicedesk.PNG)

### Populate DB with sample records

Set `spring.profiles.active` Java property value to `demo` in order to start application with some sample data.

> java -Dspring.profiles.active=demo -jar build/libs/servicedesk-0.0.1-SNAPSHOT.jar

