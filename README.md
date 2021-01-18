# XY Inc Point of Interest API
Rest API to create and retrieve points of interest.

## Involved programming languages, frameworks and tools
 - Kotlin [1.4.21]
 - Spring Boot [2.4.2]
 - Flyway [7.1.1]
 - Gradle (wrapper included) [6.7.1]
 - jUnit [5.7.0]
 - MockK [1.10.5]
 - TestContainers [1.15.1]
 - Jacoco
 - PostgreSQL [12.3]
 - Docker
 - Docker Compose

## How to run?
After this repository is cloned, you can run it through a few different ways:

### Running locally without Docker:
The project requires a PostgreSQL available to connect, the default configurations are:
```
url: jdbc:postgresql://localhost:5432/xy_inc
user: root
password: root
database: xy_inc
```
These configurations can be changed at the application.yml file.

Using Gradle wrapper:
```sh
> cd [full path of the project folder]
> ./gradlew clean build
> ./gradlew bootRun
```

### Running locally with Docker:
It requires that you have installed a Docker and Docker Compose.
```sh
> cd [full path of the project folder]
> docker-compose up
```
This will start a PostgreSQL database, build and start the application.

The started PostgreSQL database can be accessed through port `5432`.

### Testing the application:
To run both, unity and integration tests:
```sh
> cd [full path of the project folder]
> ./gradlew test
```
This will run the application tests and generate a report at
```
[project folder]/build/reports/jacoco/test/html
```
The application requires a minimum of 70% test coverage for the build to be accepted.

## Using the API
Once the application is up and running, the default HTTP port is `8080` and the swagger can be
accessed at `http://localhost:8080/swagger-ui/`

#### API Endpoints
```
POST /v1/points-of-interest HTTP/1.1
Host: localhost:8080
Content-Type: application/json
{
    "name": "Restaurant",
    "posX": 37,
    "posY": 22
}
```

```
GET /v1/points-of-interest HTTP/1.1
Host: localhost:8080
```

```
GET /v1/points-of-interest/test_id HTTP/1.1
Host: localhost:8080
```

```
GET /v1/points-of-interest/name/Restaurant HTTP/1.1
Host: localhost:8080
```

```
GET /v1/points-of-interest/near?posX=20&posY=10&maxDistance=10 HTTP/1.1
Host: localhost:8080
```
