# Backend Lab 1 - Task Management Backend

## Overview
Spring Boot application implementing:
- JPA entities for `User` and `Task`
- Repositories with custom methods
- REST APIs at `/api/users`, `/api/tasks`, `/api/auth`
- JWT-based authentication (simple demo)
- Health endpoint `/health` that checks DB connectivity
- Data initializer seeds 3 users and 5 tasks

## Run
1. Ensure MariaDB is running and create database `backend_lab1`.
2. Adjust `src/main/resources/application.properties` with DB credentials.
3. Build and run:
```
mvn spring-boot:run
```

## Tests
Run:
```
mvn test
```

## Endpoints
- `POST /api/users` - create user
- `GET /api/users` - list users
- `POST /api/auth/login` - login (body: { "email": "...", "password": "..." }) -> returns token
- `GET /api/tasks` - list tasks (filter by `priority` or `ownerId`)
- `POST /api/tasks/assign?taskId=...` - assign task to an available user

## Notes
- JWT secret in properties is for demo only. Change for production.
- Passwords are stored hashed using BCrypt.
