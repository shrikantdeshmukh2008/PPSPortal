FROM eclipse-temurin:17-jdk

COPY target/ff-shipping-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app/

EXPOSE 9094

RUN sh -c 'touch ff-shipping-service-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","ff-shipping-service-0.0.1-SNAPSHOT.jar"]