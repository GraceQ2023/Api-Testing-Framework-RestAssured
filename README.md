# API Test Automation – Restful-Booker API

## 📌 Project Overview
This project demonstrates an **API automation testing framework** for the [Restful-Booker API](https://restful-booker.herokuapp.com).  
It uses **REST Assured** and **TestNG** for automation, with **Postman collections** for initial API exploration and verification.  
The framework is designed to be scalable, reusable, and CI/CD-ready with Docker and Jenkins integration.

---

## 🛠️ Tools & Technologies
- **Java**
- **REST Assured**
- **Postman**
- **TestNG**
- **Maven**
- **Docker & Docker Compose**
- **Jenkins (CI/CD)**
- **ExtentReports**
- **Git/GitHub**

---

## ✨ Features
- Explored and verified API endpoints using **Postman collections** with assertions.
- Designed and executed **REST Assured test cases** for authentication and CRUD operations.
- Implemented **data-driven testing** using Excel (via TestNG data providers).
- Applied **LoggingFilter** to automatically log request/response details.
- Generated structured **ExtentReports** for test results.
- Maintained **utilities and TestNG listeners** to enhance reusability and debugging.
- Containerized test execution with **Docker** to ensure environment consistency.
- Integrated with **Jenkins pipelines** to run tests automatically on GitHub updates.

---

## ▶️ How to Run Tests

### 1. Run Tests Locally 
1. Clone the repository:
   ```bash
   git clone https://github.com/GraceQ2023/Api-Testing-Framework-RestAssured.git
   cd Api-Testing-Framework-RestAssured
   ```

2. Run with TestNG suite:
    ```bash
    mvn test -DsuiteXmlFile=src/test/resources/testng.xml
   ```

### 2. Run with Docker
1. Build and run container:
    ```bash
    docker compose -f docker/docker-compose.yml up --exit-code-from api-tests
   ```

## 📊 Reporting & Logging
- **Extent Report (HTML):** `target/extent-report.html` 
- **Surefire Reports (JUnit XML):** `target/surefire-reports/` 
- **API Logs (Log4j):** `logs/framework.log` and `logs/error.log` – automatic logging of all API requests and responses via `LoggingFilter`


### 📌 CI/CD Integration
- Configured Multibranch Pipeline in Jenkins.
- Integrated with GitHub webhooks via ngrok for local testing.
- Pipeline stages: Checkout → Run Tests in Docker → Publish Reports → Cleanup.