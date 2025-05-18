# 1️⃣ Base image with Java 17 and Maven to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS build

# 2️⃣ Set working directory
WORKDIR /app

# 3️⃣ Copy all files into the container
COPY . .

# 4️⃣ Run Maven to build the project and generate the .jar file
RUN mvn clean install -DskipTests

# 5️⃣ Use a lighter image for running the app
FROM eclipse-temurin:17-jdk

# 6️⃣ Set the working directory for runtime
WORKDIR /app

# 7️⃣ Copy the built .jar file from the first image
COPY --from=build /app/target/*.jar attendance-service.jar

RUN wget -O /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh

# 8️⃣ Expose the port your app runs on (change if different)
EXPOSE 8083

# 9️⃣ Command to run the application
ENTRYPOINT ["java", "-jar", "attendance-service.jar"]
