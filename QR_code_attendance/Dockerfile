# 1️⃣ Use Maven image with JDK 17 for building
FROM maven:3.9.4-eclipse-temurin-17 AS build

# 2️⃣ Set the working directory in the container
WORKDIR /app

# 3️⃣ Copy all project files into the container
COPY . .

# 4️⃣ Build the project using Maven (skip tests for faster build)
RUN mvn clean install -DskipTests

# 5️⃣ Use a lightweight Java image for running the JAR
FROM openjdk:17-jdk-slim

# 6️⃣ Set the working directory for the runtime container
WORKDIR /app

# 7️⃣ Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar qr-code-attendance.jar

RUN apt-get update && \
    apt-get install -y wget && \
    wget -O /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 8️⃣ Expose the port used by QR_CODE_ATTENDANCE service (change if needed)
EXPOSE 8082

# 9️⃣ Command to run the server
# 9️⃣ Command to run the server (disable container support to avoid cgroup errors)
#ENTRYPOINT ["java", "-Djdk.disableContainerSupport=true", "-jar", "qr-code-attendance.jar"]

#ENTRYPOINT ["java", "-Djdk.disableContainerSupport=true", "-Dmanagement.metrics.binders.system.enabled=false", "-jar", "qr-code-attendance.jar"]

ENTRYPOINT ["java", "-jar", "qr-code-attendance.jar"]
