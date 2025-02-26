FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/appointment-booking-*.jar app.jar
RUN mkdir -p /logs
VOLUME /logs
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]