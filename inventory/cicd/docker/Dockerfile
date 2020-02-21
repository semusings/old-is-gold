FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN sleep 20
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]