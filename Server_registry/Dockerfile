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
#FROM eclipse-temurin:17-jdk
#
## 6️⃣ Set the working directory for the runtime container
#WORKDIR /app
#
## 7️⃣ Copy the built JAR file from the build stage
#COPY --from=build /app/target/*.jar eureka-server.jar
#
#RUN wget -O /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
#    chmod +x /usr/local/bin/wait-for-it.sh
#
## 8️⃣ Expose Eureka's default port (change if yours is different)
#EXPOSE 8761
#
## 9️⃣ Command to run the server
#ENTRYPOINT ["java", "-jar", "eureka-server.jar"]

# 1️⃣ Use Maven image with JDK 17 for building
FROM maven:3.9.4-eclipse-temurin-17 AS build

# 2️⃣ Set the working directory in the container
WORKDIR /app

# 3️⃣ Copy all project files into the container
COPY . .

# 4️⃣ Build the project using Maven (skip tests for faster build)
RUN mvn clean install -DskipTests

# 5️⃣ Use a lightweight Java image for running the JAR
FROM eclipse-temurin:17-jdk

# 6️⃣ Set the working directory for the runtime container
WORKDIR /app

# 7️⃣ Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar eureka-server.jar

# 8️⃣ Expose Eureka's default port
EXPOSE 8761

# 9️⃣ Command to run the server
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]