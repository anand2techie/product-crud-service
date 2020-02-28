# Getting Started
Product service, developed in Java 8 and latest version of Spring Boot (2.2.1.RELEASE) 

# Command to run the application locally using maven
mvn spring-boot:run

# To Run with Docker
Build Docker image with Maven => ./mvnw com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=springboot/product-service-docker
Run as Docker image => docker run -p 8080:8080 -t springboot/product-service-docker

#Security
Please enter following credentials to access API
Username: test
Paassword: password

#Hosted in Heroku
Also hosted in Heroku server: http://product-crud-service.herokuapp.com/swagger-ui.html

# Project Design
* Adopted clean & test-driven design for developing REST APIs for CRUD operations on products.
* Adhered to REST API design standards, ensuring right HTTP methods are used with proper response codes.
* Project structure is laid out with separation of concerns in the first place. Used Hyperlinks/HATEOAS for discoverability.
* Used H2 as the database; Spring data JPA as repository support
* Lombok is used for concise-code writing

# Highlights
* Handwritten request and response models for APIs (refer rest.apimodel package)
* Dedicated controller for Exception handling (ErrorController)
* Reusable components (e.g. ProductCreateAndUpdateEntityBase & ProductEntityBase)
* Swagger Configuration (refer http://localhost:8080/swagger-ui.html)
* Added Postman collections (Product API.postman_collection)
* Extensive test coverage
* Slf4j for logging
* Paging and Sorting support
* Dockerized (Please refer Dockerfile in the root directory)