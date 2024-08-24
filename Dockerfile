FROM openjdk:17
COPY target/TaskManagementSystems-0.0.1-SNAPSHOT.jar /app/TaskManagementSystems-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD ["java", "-jar", "TaskManagementSystems-0.0.1-SNAPSHOT.jar"]