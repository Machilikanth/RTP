FROM openjdk:17-jdk-alpine
EXPOSE 8083
ARG JAR_FILE=build/libs/tourtpcore-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tourtpcore.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=dev","-jar", "/tourtpcore.jar"]