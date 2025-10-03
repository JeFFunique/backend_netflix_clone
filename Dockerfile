# Step 1: Use a lightweight JDK image
FROM eclipse-temurin:17-jdk-jammy

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy jar into container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port (Spring Boot default)
EXPOSE 8080

# Step 5: Run the application
ENTRYPOINT ["java","-jar","app.jar"]