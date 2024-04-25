# Task Management System

This is a task management system designed to help teams organize their tasks effectively. It provides features for creating, updating, assigning, and tracking tasks within a team.

## Features

- Create tasks with titles, descriptions, priorities, and assigned team members
- Update task details and status
- Assign tasks to team members
- View tasks by status, priority, or assigned team member
- Generate reports on task completion and performance

## Technologies Used

- Spring Boot
- Kotlin
- Spring Data JPA
- Postgresql Database (for development, can be replaced with other databases in production)
- Spring (for RESTful API)
- Mockito and JUnit (for unit testing)
- Gradle (for build automation)

## Project Structure

The project follows the Clean Architecture principles to ensure separation of concerns and maintainability.

- `application`: Contains application-specific logic including use cases and DTOs
- `domain`: Contains business entities and core business logic
- `presentation`: Contains controllers and DTOs for handling HTTP requests and responses
- `config`: Contains configuration classes for Spring Boot
- `service`: Contains service classes that bridge the gap between presentation and domain layers
- `repository`: Contains repository interfaces for data access

## Setup

1. Clone the repository:
2. Navigate to the project directory:
3. Build the project using Gradle:
4. Add db connections config
5. Run the application:

The application will start and be accessible at `http://localhost:8080`.

## Postman Collection

In the project, you can find a Postman collection file named "Task Management.postman_collection".

To use the collection:

1. Open Postman.
2. Import the collection file into Postman.
3. You can now see the list of API endpoints available for testing.
4. Set up the necessary environment variables if required (such as base URL).
5. Send requests to test the endpoints and observe the responses.

## Testing

Unit tests are included for service layer components. You can run the tests using Gradle:
