# Getting Started
This is simple Spring Boot service for exposing `LandParcel` entity operations. The service uses in-memory H2 database as the datastore. The project was bootstrapped using [start.spring.io](https://start.spring.io/).

### Requirements
- Java 17 +

### How to Run
Run using Spring Boot Maven plugin
```shell
./mvnw spring-boot:run
```

### Build project
```shell
./mvnw clean build
```

### Run tests
```shell
./mvnw clean test
```


### Note
- Since the services and the logic is straightforward, no documentation of the code is required. Public interfaces can be documented using Javadoc or OpenAPI/Swagger if required. 
- H2 configurations are kept to a minimum level

