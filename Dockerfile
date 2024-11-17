FROM amazoncorretto:21.0.3
WORKDIR /app
COPY build/libs/*.jar currency-convert.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/currency-convert.jar"]