FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/tourtptrs-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} rtptrs.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=dev","-jar", "/rtptrs.jar"]