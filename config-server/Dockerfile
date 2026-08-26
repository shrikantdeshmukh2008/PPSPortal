# Step 1: Use a stable base image with JDK 17
# Eclipse Temurin is the recommended OpenJDK distribution
FROM eclipse-temurin:17-jdk

# Step 2: Add maintainer information (optional, for documentation)
MAINTAINER "Shrikant Deshmukh"

# Step 3: Copy the built JAR file from your local target folder into the container
# Make sure you run 'mvn clean package' first so the JAR exists in target/
COPY target/config-server-0.0.1-SNAPSHOT.jar /usr/app/

# Step 4: Set the working directory inside the container
# All commands will run from /usr/app
WORKDIR /usr/app/

# Step 5: Expose the Config Server port
# This lets you access the dashboard at http://localhost:8888
EXPOSE 8888

# Step 6: Create a dummy 'touch' to ensure the JAR file timestamp is updated
# This is sometimes used to avoid caching issues in Docker layers
RUN sh -c 'touch config-server-0.0.1-SNAPSHOT.jar'

# Step 7: Define the startup command
# When the container runs, it will execute 'java -jar config-server-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","config-server-0.0.1-SNAPSHOT.jar"]