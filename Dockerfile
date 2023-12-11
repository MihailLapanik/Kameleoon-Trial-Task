FROM openjdk:17
WORKDIR /app
COPY build/libs/Kameleoon-Trial-Task-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "Kameleoon-Trial-Task-0.0.1-SNAPSHOT.jar"]
