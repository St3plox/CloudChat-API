FROM openjdk:17-oracle
COPY target/*.jar cloudapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cloudapp.jar"]