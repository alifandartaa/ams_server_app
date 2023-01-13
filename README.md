# Asset Management System REST API
A RESTful API example for asset management system using java spring boot (java 8) (project metrodata coding camp)

# ams_server_app

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Template [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run

## Structure
```
├── java-app
│   ├── config //Configuration Web Security
│   ├── controllers // Our API core handlers
│   ├── exceptions  // custom handling exceptions
│   ├── models  // entity model
│   ├── repos     // jparepository
│   ├── services // services each function from endpoint
│   └── utils     // class helper
└── resources
    
```

## API

#### /v1/emp/rentAsset
* `POST` : create rent request

#### /v1/history/review_rent/:id
* `PUT` : review rent request (approved/denied)

#### /v1/asset
* `POST` : create submission asset

#### /v1/asset/review_asset/:id
* `GET` : review submission asset (approved/denied)

#### /v1/report/:id
* `PUT` : create report

## Todo

- [x] Support basic REST APIs.
- [x] Support Authentication with user for securing the APIs.
- [ ] Make convenient wrappers for creating API handlers.
- [ ] Write the tests for all APIs.
- [x] Organize the code with packages
- [ ] Building a deployment process 
