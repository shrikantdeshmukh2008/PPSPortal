# Step 1: Use a stable base image with JDK 17
FROM eclipse-temurin:17-jdk

# Step 2: Add maintainer information (optional, for documentation)
MAINTAINER "Shrikant Deshmukh"

# Step 3: Copy the built JAR file from your local target folder into the container
COPY target/ff-oms-integration-service-0.0.1-SNAPSHOT.jar /usr/app/

# Step 4: Set the working directory inside the container
WORKDIR /usr/app/

# Step 5: Expose the OMS Integration Service port
EXPOSE 9091

# Step 6: Create a dummy 'touch' to ensure the JAR file timestamp is updated
RUN sh -c 'touch ff-oms-integration-service-0.0.1-SNAPSHOT.jar'

# Step 7: Define the startup command
ENTRYPOINT ["java","-jar","ff-oms-integration-service-0.0.1-SNAPSHOT.jar"]