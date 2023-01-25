FROM openjdk:11-jre-slim
FROM maven

RUN apt-get update
RUN apt-get -y install texlive
RUN apt-get -y install pandoc

COPY . .

RUN mvn -f pom.xml package
RUN export PORT=5000

CMD ["sh", "target/bin/simplewebapp"]