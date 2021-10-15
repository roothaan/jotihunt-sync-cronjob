#
# Build stage
#
FROM maven:3.8-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/jotihunt-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/jotihunt.jar
EXPOSE 8080
ENTRYPOINT ["java","-cp","/usr/local/lib/jotihunt.jar", "JotihuntSync"]
