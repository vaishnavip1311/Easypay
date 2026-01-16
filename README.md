# Easypay – The Payroll Management System

Easypay is a **Payroll Management System** designed to automate, streamline, and secure payroll operations for organizations. The system improves accuracy, ensures statutory compliance, reduces processing time, and enhances employee satisfaction through a self-service portal.

This project was developed as part of a structured enterprise-grade application design, following industry best practices and guidelines.

---


## 🎯 Scope of the Project

* **Automated Payroll Calculations** – Accurate calculation of salaries, deductions, and benefits
* **Streamlined Data Entry** – Error-resistant employee and payroll data management
* **Compliance Management** – Ensures adherence to tax laws and labor regulations
* **Employee Self-Service Portal** – Employees can access pay slips, tax details, and leave information
* **Timely Salary Disbursement** – Ensures accurate and on-time payroll processing

---

## 🧩 Actors & Use Cases

### 👤 Admin / HR Manager

* Manage employee information
* User and role management
* Define payroll policies
* Generate payroll
* Compliance reporting
* Audit trail and notifications

### 💼 Payroll Processor

* Calculate payroll
* Verify payroll data
* Manage benefits
* Process salary payments
* View audit logs

### 🧑‍💻 Employee

* View pay stubs
* Update personal information
* Submit timesheets
* Request leave
* Receive notifications

### 👨‍💼 Manager / Supervisor

* Review team payrolls
* Approve leave requests
* View team-level dashboards

---

## 🛠️ Technologies Used

| Layer          | Technology                                       |
| -------------- | ------------------------------------------------ |
| Frontend       | React.js                                         |
| Backend        | Java (Spring Boot)                               |
| Database       | MySQL                                            |
| Authentication | JWT (JSON Web Tokens)                            |

---

## 🔐 Security & Authentication

### JWT Authentication

* Token generated upon successful login
* Token includes user ID, role, and expiration
* Signed using a secure server-side secret key
* Stored securely on the client side

### JWT Authorization

* Protected routes require valid tokens
* Token verification on each request
* Role-based access control (RBAC)

### Logout

* Token invalidation to prevent unauthorized access

---

## ⚙️ Development Process Overview

### Admin / HR Module

* Dashboard with payroll metrics
* Employee onboarding & offboarding
* Payroll policy configuration
* Compliance reporting
* Notification center
* Audit trail

### Payroll Processor Module

* Payroll execution dashboard
* Payroll validation & verification
* Exception handling
* Audit trail and alerts

### Employee Module

* Self-service portal
* Pay slips and tax documents
* Leave management
* Notifications

### Manager / Supervisor Module

* Team dashboards
* Leave approvals
* Team payroll access

---

## 📐 Backend Development Guidelines

* RESTful APIs using Spring Boot
* ORM-based database interaction
* Backend validations
* API versioning
* Centralized logging
* Configurable DB connections & URLs
* Unit testing
* JWT-based security
* Standard coding practices and layered architecture

---

## 🎨 Frontend Design Guidelines

* Responsive layout using modern CSS / frameworks
* Clean UI with consistent color schemes and typography
* Form validations with user-friendly error messages
* Login & registration pages
* Hover & focus effects
* Client-side validation
* Routing and navigation

---


## ▶️ How to Run Easypay – Payroll Management System

### ✅ Prerequisites

Make sure the following are installed on your system:

* Java JDK 11 or above
* Maven
* MySQL 
* React.js & npm (for frontend)
* IDE: IntelliJ IDEA / Eclipse / VS Code
* Git

---

### 📥 Step 1: Clone the Repository

```bash
git clone https://github.com/vaishnavip1311/Easypay.git
cd Easypay
```

---

### ⚙️ Step 2: Backend Setup (Spring Boot)

#### 1️⃣ Open Backend Project

* Open the `easypay` folder in your IDE
* Ensure `pom.xml` loads without errors

#### 2️⃣ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/easypay
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Create database:

```sql
CREATE DATABASE easypay;
```

---

#### 3️⃣ Build & Run Backend

Using terminal:

```bash
mvn clean install
mvn spring-boot:run
```

Backend runs at:

```
http://localhost:8080
```

---

### 🎨 Step 3: Frontend Setup (React / Angular)

#### React

```bash
cd easypay-ui
npm install
npm start
```

Runs at:

```
http://localhost:3000
```

#### Angular (if applicable)

```bash
cd easypay-ui
npm install
ng serve
```

Runs at:

```
http://localhost:4200
```

---

### 🔐 Step 4: Authentication Flow

1. Register user (Admin / Employee)
2. Login using credentials
3. Backend generates JWT token
4. Token sent in request headers

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

### 🧪 Step 5: Testing APIs

* Use  Postman
* Test login, employee management, payroll processing, leave requests

---

### 🛑 Common Issues

**Port conflict**

```properties
server.port=8081
```

**Database connection error**

* Ensure DB service is running
* Verify credentials

**CORS issue**

```java
@CrossOrigin(origins = "http://localhost:3000")
```

## 👩‍💻 Author

**Vaishnavi Sharad Patil**
Computer Science & Engineering (2025 Batch)

---

✨ *Easypay aims to deliver a secure, scalable, and efficient payroll solution aligned with real-world industry requirements.*
