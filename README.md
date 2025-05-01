# 🌤️ Weather API – Spring Boot Application

This is a Spring Boot-based Weather API project that integrates with an external weather service and provides features like JWT authentication, caching, multithreading with virtual threads, and Docker support.

---

## 📅 Weekly Implementation Plan

### **Week 1: External API Integration**
- Integrated with an external Weather API to fetch weather data.

### **Week 2: Data Access & DTOs**
- Utilized `JpaRepository` for database interactions.
- Implemented Data Transfer Objects (DTOs) across the project, including for responses from the external Weather API.
- **Bonus:** Implemented an email service to send weather updates to users who have notifications enabled.

### **Week 3: Security & Request Tracking**

- Integrated JWT for authentication and authorization.
- Used AOP (Aspect-Oriented Programming) to log weather request details (by location and coordinates) into the `RequestHistory` table.
- Created custom exceptions for both the user and email services.
- Added extensive logging throughout the application, especially in the `JwtFilter`.
- **Bonus:** Integrated Swagger UI for interactive API documentation.

### **Week 4: Performance Enhancements**
- Implemented caching on the route specified in the project requirements.
- Introduced multithreading using **Virtual Threads** to improve scalability and responsiveness.

### **Week 5: Testing & Containerization**
- Wrote unit tests for the controller layer.
- Created a `Dockerfile` to containerize the REST API.
- Created a personal `docker-compose.yml` file to orchestrate the Spring app and PostgreSQL database.

---

## ✨ Additional Improvements
- Enabled automatic validation on all database columns for improved data integrity.

---

## 🛠️ Technologies Used
- **Java + Spring Boot**
- **PostgreSQL**
- **JWT** for authentication
- **Swagger UI** for API documentation
- **Spring Data JPA (Hibernate)**
- **AOP** for logging request history
- **Virtual Threads** (JDK 21+)
- **Spring Cache**
- **Docker & Docker Compose**
- **JUnit & Mockito** for unit testing
