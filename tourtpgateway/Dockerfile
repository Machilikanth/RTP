FROM openjdk:17-jdk-alpine
EXPOSE 8082
ARG JAR_FILE=build/libs/tourtpgateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tourtpgateway.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=dev","-jar", "/tourtpgateway.jar"]