FROM openjdk:11-jre-slim

RUN sudo apt install maven
RUN sudo apt-get install pandoc

COPY lst20/devops-java/src src
COPY lst20/devops-java/pom.xml pom.xml
COPY lst20/devops-java/system.properties system.properties

RUN mvn package

RUN export PORT=5000
RUN sudo nohup target/bin/simplewebapp > output.txt 2>&1 &
