
# 📚 Spring Boot Library Application

A simple Spring Boot application for managing a library of books. It supports full CRUD operations, basic validation, and includes unit tests.

## 🚀 Features

- Add new books
- View all books
- Get a book by ID
- Update existing books
- Delete books

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3.5.3
- Maven
- H2 Database (in-memory)
- JUnit & Spring MockMvc for testing
- Docker

## 📦 API Endpoints

| Method | Endpoint        | Description             |
|--------|-----------------|-------------------------|
| GET    | /books          | Get all books           |
| GET    | /books/{id}     | Get a book by ID        |
| POST   | /books          | Add a new book          |
| PUT    | /books/{id}     | Update an existing book |
| DELETE | /books/{id}     | Delete a book           |

## 🧪 Running Tests

```bash
mvn test
```

There are 10+ unit tests for:
- Successful creation, reading, updating, and deletion of books.
- Error handling when a book is not found.
- Validation of request bodies.

## 🐳 Docker Instructions

### 1. Build Docker Image

```bash
docker build -t springboot-library .
```

### 2. Run Docker Container

```bash
docker run -p 8080:8080 springboot-library
```

## 🌐 Testing Locally

Once the container is running, access the app at:

```
http://localhost:8080/books
```

Use tools like Postman or curl to interact with the REST API.

## ⚙️ Future Improvements

- Integrate PostgreSQL with Docker Compose
- Add authentication/authorization
- Expand tests to include integration tests
