# online auction shop

Spring boot application using mongo database with  docker configuration.

## Documentation

- [Requirements](#requirements)
- [Project setup](#environment-setup)
  - [Repository](#repository)
  - [Properties](#properties)
  - [.env file](#.env)
- [Getting started](#getting-started)
  - [Build project](#build-project)
  - [Run project](#run-project)
  - [Run unit tests](#run-unit-tests)
  - [Functionalities tests](#functionalities-tests)
  - [Postman](#postman)
  - [Endpoints](#endpoints)
- [Libraries](#Libraries)



## Requirements

Project runs on Java 11 and Gradle 7.6 or later.
Docker is also mandatory due to a network of containers
created by docker-compose.

## Environment setup

### Repository

Clone the online-auction-shop repo:

```
git clone https://github.com/Tchorson/online_auction_shop.git
```

### Properties file

Create `jwt.properties` file and add secret key 

```
secret.key=<secret_value>
```

### .env file

The project requires `.env` file for docker-compose.
The file should be in the following format:

```
MONGO_DATA_HOST_PATH="./mongo/data"
MONGO_LOG_HOST_PATH="./mongo/logs"
MONGO_INITDB_SCRIPTS_HOST_PATH="./scripts"
MONGO_ROOT_USERNAME=<root>
MONGO_ROOT_PASSWORD=<rootpassword>
MONGO_DB_USERNAME=<user>
MONGO_DB_PASSWORD=<userpassword>
```

## Getting Started

### Build project

The application can be built from the Intellij Idea or by `gradlew clean build` command. The build artifacts will be stored in the `build/libs/` directory

### Run project

 `docker-compose --file docker-compose.yml --env-file .env up` will run both mongo database and service.
Database will be automatically initialized by `mongo-init.js` script that is located in scripts folder.
By default, there are 2 users, a normal user and admin.

### Run unit tests

Unit tests can be run from the Intellij Idea or by `gradlew test` command.

### Functionalities tests

- Run docker-compose command and wait until 2 containers will not be ready.
- Start postman.
- Go to personal workspace and navigate to `Environments`. Click `import` and select `shop_endpoints.postman_environment.json` file.
- Go to `Collections` and click `import` and select  `local_environment.postman_environment.json` file.
- Click `Post auth admin` or `Post auth user`, depending on your choice and send the request.
- Copy the token from response and choose another endpoint and click it.
- Put paste the token in `Authorization` in Bearer Tokenn text input.   Token will expire within 1 hour.

### Postman

In `postman` folder has been put postman collection with all prepared endpoints along with environment variables.

### Endpoints

- POST `/api/v1/auth` - returns JWT token role based on secret key from jwt.properties, username and password. Json required.
- GET `/api/v1/orders/list` - admin can list all orders, normal users can list their own orders.
- GET `/api/v1/orders/{user}` - list orders for specific user. Admin access only. Normal user will receive 403 response.
- DELETE `/api/v1/orders/remove` - removes an order. Admins only. Order id required. 
- POST `/api/v1/orders/add` - add new order for both roles. Json required.

### Libraries

- spring-boot-starter-data-mongodb : Mongo repositories and annotations
- spring-boot-starter-security : Users role specific authorization
- spring-boot-starter-web : building web, RESTful, applications using Spring MVC
- spring-boot-starter-test : testing Spring Boot applications with JUnit Jupiter, Hamcrest and Mockito
- spring-boot-starter-validation : Java Bean Validation,  for consistent data flow in endpoints
- jsonwebtoken : JSON Web Token Support
- lombok : Code reduction