FROM openjdk:17
MAINTAINER test.com
VOLUME /tmp
EXPOSE 8080
ADD target/challenge-tenpo-1.0.jar challenge.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/challenge.jar"]