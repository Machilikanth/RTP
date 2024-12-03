FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/tourtpkyc-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} rtpkyc.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=dev","-jar", "/rtpkyc.jar"]