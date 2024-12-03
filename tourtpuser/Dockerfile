FROM openjdk:17-jdk-alpine
EXPOSE 8081
ENV TZ=Asia/Kolkata
ARG JAR_FILE=build/libs/tourtpuser-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} rtpuser.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=dev","-jar", "/rtpuser.jar"]