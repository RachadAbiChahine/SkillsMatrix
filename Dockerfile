FROM openjdk:11.0.3-jre-stretch
VOLUME /tmp
COPY  target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app.jar"]
EXPOSE 8080