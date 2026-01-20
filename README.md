# 🛒 Minimal E-Commerce Backend API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0+-green)
![MongoDB](https://img.shields.io/badge/MongoDB-Database-forestgreen)

A clean, modular, and production-inspired **Spring Boot + MongoDB** backend for an e-commerce platform.  
This project is designed to demonstrate **backend architecture**, **RESTful API design**, and **real-world e-commerce workflows** such as carts, orders, and payments.

---

## ✨ What This Project Covers

* Scalable backend structure following industry best practices
* RESTful APIs with clear separation of concerns
* MongoDB persistence using Spring Data
* End-to-end e-commerce flow (Cart → Order → Payment)
* Mock payment gateway with webhook handling
* Easy local setup for learning and testing

---

## 🚀 Core Features

### 👤 User Management
* Create and manage users

### 📦 Product Catalog
* Add, view, and manage products

### 🛒 Cart Operations
* Add/remove products
* Update quantities
* View cart summary

### 📦 Order Management
* Create orders from cart
* Track order status

### 💳 Payments
* Mock payment processing
* Webhook simulation for payment confirmation

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot**
* **MongoDB**
* **Maven**
* **REST APIs**
* **Postman** (for API testing)

---

## 📂 Project Structure

text
src/main/java/com/example/ecommerce
├── controller   # REST API controllers
├── service      # Business logic
├── repository   # MongoDB repositories
├── model        # Domain entities
├── dto          # Request / Response DTOs
├── config       # Application & database configuration
└── webhook      # Payment webhook handlers

---

## ⚙️ Running the Project Locally

### Prerequisites

1. **Java 17** or higher
2. **MongoDB** (running locally or via Docker)
3. **Maven**

### Steps

1. **Clone the repository**

git clone [https://github.com/not-so-shubh/Minimal-E-Commerce-Backend-API.git](https://github.com/not-so-shubh/Minimal-E-Commerce-Backend-API.git)
cd Minimal-E-Commerce-Backend-API



2. **Run the application**

mvn spring-boot:run



3. **Access the API**
The application will start at:
http://localhost:8080




---

## 📖 Documentation

The project is supported by the following documentation files:

* **[API_DOCUMENTATION.md](https://www.google.com/search?q=API_DOCUMENTATION.md)** Complete REST API documentation with endpoints, payloads, and responses.
* **[QUICK_REFERENCE.md](https://www.google.com/search?q=QUICK_REFERENCE.md)** Concise cheat-sheet for commonly used APIs and flows.
* **[ENTITY_STRUCTURE.md](https://www.google.com/search?q=ENTITY_STRUCTURE.md)** Overview of domain models and MongoDB entity relationships.

---

## 🧪 Testing the APIs

* Use **Postman** to test endpoints.
* Import requests manually or create your own collections.
* Refer to the documentation files for request examples.

---

## 🎯 Intended Use

* Learning Spring Boot + MongoDB backend development
* Understanding clean backend architecture
* Portfolio and interview preparation
* API design practice

---

## 👤 Author

**Shubh Jaiswal**

---

## 📜 License

This project is intended for **educational and portfolio use**.
