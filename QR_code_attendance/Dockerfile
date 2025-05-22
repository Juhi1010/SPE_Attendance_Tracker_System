## 1️⃣ Use Maven image with JDK 17 for building
#FROM maven:3.9.4-eclipse-temurin-17 AS build
#
## 2️⃣ Set the working directory in the container
#WORKDIR /app
#
## 3️⃣ Copy all project files into the container
#COPY . .
#
## 4️⃣ Build the project using Maven (skip tests for faster build)
#RUN mvn clean install -DskipTests
#
## 5️⃣ Use a lightweight Java image for running the JAR
#FROM openjdk:17-jdk-slim
#
## 6️⃣ Set the working directory for the runtime container
#WORKDIR /app
#
## 7️⃣ Copy the built JAR file from the build stage
#COPY --from=build /app/target/*.jar qr-code-attendance.jar
#
## 8️⃣ Expose the port used by QR_CODE_ATTENDANCE service
#EXPOSE 8082
#
## 9️⃣ Command to run the server
#ENTRYPOINT ["java", "-jar", "qr-code-attendance.jar"]


# 1️⃣ Use Maven image with JDK 17 for building
#FROM maven:3.9.4-eclipse-temurin-17 AS build
#
## 2️⃣ Set the working directory in the container
#WORKDIR /app
#
## 3️⃣ Copy all project files into the container
#COPY . .
#
## 4️⃣ Build the project using Maven (skip tests for faster build)
#RUN mvn clean install -DskipTests
#
## 5️⃣ Use a lightweight Java image for running the JAR
#FROM openjdk:17-jdk-slim
#
## 6️⃣ Set the working directory for the runtime container
#WORKDIR /app
#
## 7️⃣ Copy the built JAR file from the build stage
#COPY --from=build /app/target/*.jar qr-code-attendance.jar
#
## 8️⃣ Expose the port used by QR_CODE_ATTENDANCE service
#EXPOSE 8082
#
## 9️⃣ Command to run the server with JVM args for cgroup awareness
#ENTRYPOINT ["java", "-Djdk.attach.allowAttachSelf=true", "-Djdk.internal.platform.cgroup.useRootless=true", "-jar", "qr-code-attendance.jar"]


# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy all project files into the container
COPY . .

# Build the project using Maven (skip tests for faster build)
RUN mvn clean install -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-focal

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar qr-code-attendance.jar

# Expose the port used by QR_CODE_ATTENDANCE service
EXPOSE 8082

# Command to run with metrics disabled
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:+UnlockExperimentalVMOptions", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dmicrometer.binder.process.enabled=false", \
            "-Dmicrometer.binder.system.enabled=false", \
            "-Dmicrometer.binder.jvm.enabled=false", \
            "-jar", "qr-code-attendance.jar"]