FROM alpine:latest
MAINTAINER Arkajyoti Nag(arka.imps@gmail.com)
RUN apk update
RUN apk add --no-cache openjdk8
RUN apk add --no-cache maven
COPY pom.xml /AutomationPractise/pom.xml
COPY src /AutomationPractise/src
COPY testng.xml /AutomationPractise/testng.xml
COPY target/Automation-0.0.1-SNAPSHOT.jar /AutomationPractise/Automation-0.0.1-SNAPSHOT.jar
WORKDIR /AutomationPractise