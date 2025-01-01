# Use an official Maven image to build the app
FROM maven:3.8.4-jdk-11-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy your pom.xml and the source code into the container
COPY pom.xml .
COPY src /app/src

# Run Maven to build the app
RUN mvn clean install

# Use a smaller JDK runtime image to run the app
FROM openjdk:11-jre-slim

# Copy the .war file from the build stage
COPY --from=build /app/target/FactoryFlow2.war /app/FactoryFlow2.war

# Expose the port that your app will run on
EXPOSE 8080

# Start the app using the .war file
CMD ["java", "-jar", "/app/FactoryFlow2.war"]
