# online auction shop

Spring boot application using mongo database with  docker configuration.

## Documentation

- [Requirements](#requirements)
- [Project setup](#environment-setup)
  - [Repository](#repository)
  - [Properties files](#properties-files)
- [Getting started](#getting-started)
  - [Build project](#build-project)
  - [Run project](#run-project)
  - [Run unit tests](#run-unit-tests)
  - [Functionality tests](#functionality-tests)
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

### Properties files

The project needs a secret key in order to generate JWT token. All you need to do is create `jwt.properties` file in `resources` folder and add secret key in the following format:

```
secret.key=<secret_value>
```

### .env file

The project requires `.env` file in `online_auction_shop` directory for docker-compose.
The file should be in the following format:

```
MONGO_DATA_HOST_PATH="./mongo/data"
MONGO_LOG_HOST_PATH="./mongo/logs"
MONGO_INITDB_SCRIPTS_HOST_PATH="./scripts"
MONGO_ROOT_USERNAME=<root_name>
MONGO_ROOT_PASSWORD=<root_password>
MONGO_DB_USERNAME=<user_name>
MONGO_DB_PASSWORD=<user_password>
```

## Getting Started

### Build project

The application can be built from the Intellij Idea gradle tasks (download dependencies and press Tasks->build->build) or by `gradlew clean build` command. The build artifacts will be stored in the `build/libs/` directory

### Run project

After creating both `jwt.properties` and `.env` files and building gradle process, execute following command in terminal:
 `docker-compose --file docker-compose.yml --env-file .env up`. It will download mongo database image, build spring service image and create two containers respectively.
Database will be automatically initialized by `mongo-init.js` script that is located in scripts folder.
By default, there are 2 users, a normal user and admin, and two orders made by normal user.

### Run unit tests

Unit tests can be run from the Intellij Idea or by `gradlew test` command.

### Functionality tests

- Run the docker-compose command and wait until 2 containers will not be ready.
- Start postman.
- In postman, navigate to `Environments`. Click `import` and select `local_environment.postman_environment.json` file from `online_auction_shop/postman/environments`.
- Go to `Collections` and click `import` and select  `shop_endpoints.postman_environment.json` file from `online_auction_shop/postman/` folder. Shop endpoints should appear.
- Click `Post auth admin` or `Post auth user`, depending on your choice. In each should be body with necessary credentials. Send the request and copy the token from the server response. The token will be valid for the next 60 minutes.
- Click on another endpoint (any but `post auth admin` or `post auth user`). 
- Go to authorization. Choose Bearer token type and paste the token into Token input text. 
- Click send and wait for server response. A list of endpoints with description has been created below.

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