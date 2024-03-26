# Getting Started
This is simple Spring Boot service for exposing `LandParcel` entity operations. The service uses in-memory H2 database as the datastore. The project was bootstrapped using [start.spring.io](https://start.spring.io/).

The service exposes get_all, get_by_id, create, update and delete operations under `/landparcels` endpoint.

### Requirements
- Java 17 +

### How to Run
Run using Spring Boot Maven plugin
```shell
./mvnw spring-boot:run
```
Test create endpoint with `curl`
```shell
curl --location --request POST 'http://localhost:8080/landparcels' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "test_land",
    "status": "SHORT_LISTED",
    "area": 50.0,
    "constraints": false
}'
```

### Build Project
```shell
./mvnw clean install
```

### Run Tests
```shell
./mvnw clean test
```


### Note
- Since the services and the logic is straightforward, no documentation of the code is required. Public interfaces can be documented using Javadoc or OpenAPI/Swagger if required. 
- H2 configurations are kept to a minimum level

