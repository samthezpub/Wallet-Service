FROM openjdk
COPY ./target/Wallet-Service-1.0-SNAPSHOT.jar /app/Wallet-Service-1.0-SNAPSHOT.jar
COPY ./src/main/resources/liquibase.properties /app/src/main/resources/liquibase.properties
WORKDIR /app
CMD ["java", "-jar", "Wallet-Service-1.0-SNAPSHOT.jar"]
