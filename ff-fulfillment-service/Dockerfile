FROM eclipse-temurin:17-jdk

COPY target/ff-fulfillment-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app/

EXPOSE 9093

RUN sh -c 'touch ff-fulfillment-service-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","ff-fulfillment-service-0.0.1-SNAPSHOT.jar"]