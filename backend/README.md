# Fitness Application

A comprehensive fitness tracking application with features for workout tracking, meal planning, and progress monitoring.

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)

## Overview

This application helps users track their fitness journey by providing tools for:

- Workout planning and tracking
- Meal and nutrition logging
- Progress monitoring
- Goal setting and tracking

The system consists of a Spring Boot backend with a MySQL database and is designed to be used with a frontend application.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java JDK 17 or higher
- Maven 3.6+ (or use the included Maven wrapper)
- MySQL 8.0 or higher
- An IDE like IntelliJ IDEA, Eclipse, or VS Code (optional)

## Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd fit_app
   ```

2. **Configure the database**

   - Create a MySQL database named `webdatabase`

   ```sql
   CREATE DATABASE webdatabase;
   ```

   - The default configuration uses:
     - Username: `root`
     - Password: `root`
   - If you need different credentials, modify `backend/src/main/resources/application.properties`

3. **Build the backend**

   ```bash
   cd backend
   ./mvnw clean install
   ```

   For Windows:

   ```bash
   mvnw.cmd clean install
   ```

## Running the Application

### Running the Backend

1. **Start the Spring Boot application**

   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

   For Windows:

   ```bash
   mvnw.cmd spring-boot:run
   ```

2. **Verify the backend is running**
   - Open your browser and navigate to `http://localhost:8080`
   - You should see a Spring Boot default page or your application's homepage

### API Endpoints

The backend exposes RESTful APIs at `http://localhost:8080/api/`

#### Authentication

- `POST /api/users/register` - Register a new user
- `POST /api/users/login` - Login and get JWT token
- `POST /api/users/logout` - Logout

#### Workouts

- `GET /api/workouts` - Get all workouts
- `GET /api/workouts/{id}` - Get specific workout
- `GET /api/workouts/user/{userId}` - Get workouts by user ID
- `POST /api/workouts` - Create new workout
- `PUT /api/workouts/{id}` - Update workout
- `DELETE /api/workouts/{id}` - Delete workout

#### Exercises

- `GET /api/exercises` - Get all exercises
- `GET /api/exercises/{id}` - Get specific exercise
- `GET /api/exercises/category/{category}` - Get exercises by category
- `POST /api/exercises` - Add new exercise
- `PUT /api/exercises/{id}` - Update exercise
- `DELETE /api/exercises/{id}` - Delete exercise

#### Meals

- `GET /api/meals` - Get all meals
- `GET /api/meals/{id}` - Get specific meal
- `GET /api/meals/user/{userId}` - Get meals by user ID
- `GET /api/meals/user/{userId}/date/{date}` - Get meals for specific date
- `POST /api/meals` - Add new meal
- `PUT /api/meals/{id}` - Update meal
- `DELETE /api/meals/{id}` - Delete meal

Similar endpoints exist for meal items, user goals, and progress tracking.

## Project Structure

### Backend

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/backend/
│   │   │   ├── config/         # Configuration classes
│   │   │   ├── controller/     # REST API controllers
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── model/          # Entity classes
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── security/       # Security configuration
│   │   │   ├── service/        # Business logic layer
│   │   │   ├── util/           # Utility classes
│   │   │   └── BackendApplication.java  # Main application class
│   │   └── resources/
│   │       └── application.properties  # Application configuration
│   └── test/                  # Test classes
└── pom.xml                    # Maven configuration
```

## Security

The application uses JWT (JSON Web Token) for authentication. When users login or register, they receive a token that must be included in the `Authorization` header for subsequent requests.

## Database Schema

The application uses the following main entities:

- User - Stores user account information
- Workout - Contains workout sessions
- Exercise - Stores exercise information
- Meal - Records meal entries
- MealItem - Contains individual food items in meals
- UserGoal - Stores user fitness goals
- ProgressTracking - Tracks user progress over time

The database schema is automatically created/updated when the application starts (using JPA/Hibernate).
