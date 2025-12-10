# Study Platform Server

This repository contains the backend server and a desktop client for a collaborative study platform. The application allows users to create and join study groups, manage tasks, and share resources.

The project is divided into two main components:
*   **`study-platform-server`**: A Spring Boot application that provides a RESTful API and WebSocket endpoint for managing the platform's data.
*   **`study-platform-client`**: A JavaFX desktop application that serves as a user interface for interacting with the server.

## Features

*   **User Management**: User registration and login with password hashing.
*   **Study Groups**: Create, read, update, and delete study groups.
*   **Membership**: Add users to study groups.
*   **Task Management**: Create tasks within a group, and update their status (OPEN, IN_PROGRESS, DONE).
*   **Resource Sharing**: Add web-based resources (links) to a group.
*   **Real-time Notifications**: Uses WebSockets to notify connected clients about newly created tasks in real-time.
*   **Activity Logging**: Tracks key actions like user registration, group creation, and task updates.

## Technology Stack

### Backend (Server)
*   **Framework**: Spring Boot
*   **Language**: Java
*   **Database**: SQLite (managed via Spring JDBC)
*   **Real-time**: Spring WebSocket
*   **Authentication**: Spring Security (BCrypt for password hashing)
*   **Build Tool**: Gradle

### Desktop Client
*   **Framework**: JavaFX
*   **Language**: Java
*   **HTTP Client**: Java 11+ `HttpClient`
*   **JSON Parsing**: Google Gson
*   **Build Tool**: Maven

## Getting Started

### Prerequisites
*   Java Development Kit (JDK) 21 or newer.
*   An internet connection for downloading dependencies.

### Running the Server
1.  Navigate to the root directory of the repository.
2.  Run the application using the Gradle wrapper.
    *   On macOS/Linux:
        ```sh
        ./gradlew bootRun
        ```
    *   On Windows:
        ```sh
        gradlew.bat bootRun
        ```
3.  The server will start on `http://localhost:8080`. An SQLite database file named `study.db` will be created in the root directory.

### Running the Client
1.  Open a new terminal window.
2.  Navigate to the `study-platform-client` directory.
3.  Run the application using the Maven JavaFX plugin:
    ```sh
    mvn javafx:run
    ```
4.  The JavaFX client application will launch and connect to the locally running server. You can register a new user or log in to start using the platform.

## API Endpoints

The server exposes the following RESTful API endpoints.

### Authentication (`/api/auth`)
*   `POST /register`: Registers a new user.
*   `POST /login`: Authenticates a user and returns their details.

### Users (`/api/users`)
*   `GET /`: Retrieves a list of all users.
*   `GET /{id}`: Retrieves a specific user by their ID.
*   `GET /by-email`: Retrieves a user by their email address.
*   `PUT /{id}`: Updates a user's profile information (name and email).

### Study Groups (`/api/groups`)
*   `GET /`: Retrieves all study groups.
*   `POST /`: Creates a new study group.
*   `PUT /{id}`: Updates an existing study group.
*   `DELETE /{id}`: Deletes a study group.

### Memberships (`/api/memberships`)
*   `POST /`: Adds a user to a study group.
*   `GET /by-group/{groupId}`: Lists all members of a specific group.
*   `GET /by-user/{userId}`: Lists all groups a user is a member of.

### Tasks (`/api/tasks`)
*   `GET /by-group/{groupId}`: Retrieves all tasks for a specific group.
*   `POST /`: Creates a new task within a group.
*   `PATCH /{taskId}/status`: Updates the status of a specific task.

### Resources (`/api/resources`)
*   `GET /by-group/{groupId}`: Retrieves all resources for a specific group.
*   `POST /`: Adds a new resource to a group.

### Activity Log (`/api/activity`)
*   `GET /`: Retrieves all activity logs.
*   `GET /by-group/{groupId}`: Retrieves activity logs for a specific group.
*   `GET /by-user/{userId}`: Retrieves activity logs for a specific user.

### WebSocket (`/ws/tasks`)
*   A WebSocket endpoint for broadcasting real-time notifications about new tasks. Clients can connect to this endpoint to receive live updates.

## Database Schema

The application uses an SQLite database with the following tables defined in `src/main/resources/schema.sql`:

*   **`users`**: Stores user information, including credentials.
*   **`study_groups`**: Stores information about study groups.
*   **`memberships`**: Maps users to the study groups they have joined.
*   **`tasks`**: Contains tasks associated with each study group.
*   **`resources`**: Stores study materials (links) for each group.
*   **`activity_log`**: Records significant events that occur in the system.
