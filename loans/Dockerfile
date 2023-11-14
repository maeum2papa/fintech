FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} loans.jar
ENTRYPOINT ["java","-jar","/loans.jar"]