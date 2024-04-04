# TVShow Manager Application

**This is a backend assignment of ABN AMRO. The REST API is made with the Spring Boot framework.**

## Running the Application Locally

To run the application locally, follow these steps:

### Prerequisites

- Java (17)
- Maven
- Docker (Optional)

### Instructions

1. Navigate to the project directory:

```
cd tvshow-manager
```

2. (optional) Build the application using Maven. It is not needed since there is a build jar file provided (
   target/tvshow-manager-0.0.1-SNAPSHOT.jar):

```
mvn clean package
```

3. Run the application:

```
java -jar target/tvshow-manager-0.0.1-SNAPSHOT.jar
```

-or-

```
docker build -t tvshow .
docker run -p 8080:8080 tvshow
```

4. Access the application in your web browser | Postman at http://localhost:8080/api/v1/tv-shows

### Notes

- The application uses an H2 in-memory database, so no additional setup is required.

- Retrieve all TV shows --> uses pagination. To paginate to the next page do a call to the following: /api/v1/tv-shows?page=[page]&size=[size]. These are optional params. The default size is 5.

- Search TV show by genre --> uses the same endpoint as the bullet point above (/api/v1/tv-shows). To search by genre, just add the genre param as such: /api/v1/tv-shows?genre=comedy

- There are also endpoints for creating, reading, updating and deleting genres (/api/v1/genres).

- RateLimiting is added for the GET endpoints, however this is set to 100 requests per minute. So for testing the application, this should be fine. You can change it in the application.properties file.
