FROM alpine:latest
MAINTAINER Arkajyoti Nag(arka.imps@gmail.com)
RUN apk update
RUN apk add --no-cache openjdk8
RUN apk add --no-cache curl tar bash procps
RUN apt-get install -y maven
COPY pom.xml /usr/local/share/AutomationPractise/pom.xml
COPY src /usr/local/share/AutomationPractise/src
COPY testng.xml /usr/local/share/AutomationPractise/testng.xml
COPY target/Automation-0.0.1-SNAPSHOT.jar /usr/local/share/AutomationPractise/Automation-0.0.1-SNAPSHOT.jar
WORKDIR /AutomationPractise