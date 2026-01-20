📄 README.md
# Minimal E-Commerce Backend API

A clean and modular **Spring Boot + MongoDB** backend for an e-commerce platform.  
This project focuses on backend architecture, RESTful API design, and real-world workflows such as cart handling, orders, and payments.

---

## 🚀 Features

- User management
- Product catalog
- Cart operations
- Order creation and tracking
- Mock payment processing
- Payment webhook handling
- RESTful API design
- MongoDB persistence

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- MongoDB
- Maven
- REST APIs
- Postman (API testing)

---

## 📂 Project Structure



src/main/java/com/example/ecommerce
├── controller # REST controllers
├── service # Business logic
├── repository # MongoDB repositories
├── model # Domain entities
├── dto # Request / Response objects
├── config # Configuration classes
└── webhook # Payment webhook handlers


---

## ⚙️ Run Locally

### Prerequisites
- Java 17+
- MongoDB
- Maven

### Steps
```bash
git clone https://github.com/not-so-shubh/Minimal-E-Commerce-Backend-API.git
cd Minimal-E-Commerce-Backend-API
mvn spring-boot:run


Application runs on:

http://localhost:8080

📖 Documentation

API_DOCUMENTATION.md

MANDATORY_APIS.md

QUICK_REFERENCE.md

ENTITY_STRUCTURE.md

🧪 Testing

Use Postman.
See BONUS_POSTMAN_GUIDE.md.

👤 Author

Shubh Jaiswal

📜 License

Educational and portfolio use.


---

## 📄 `API_DOCUMENTATION.md`

```md
# API Documentation

Detailed documentation for all REST APIs exposed by the application.

---

## Base URL


http://localhost:8080


---

## Modules

- User APIs
- Product APIs
- Cart APIs
- Order APIs
- Payment APIs
- Webhook APIs

---

Each endpoint includes:
- HTTP method
- Endpoint path
- Request body
- Response body
- Description

See `MANDATORY_APIS.md` for required endpoints.

📄 MANDATORY_APIS.md
# Mandatory APIs

Core APIs required for basic e-commerce functionality.

---

## User
- Create user
- Get user details

## Product
- Create product
- Get all products
- Get product by ID

## Cart
- Add item
- View cart
- Remove item
- Clear cart

## Order
- Create order
- View order

## Payment
- Initiate payment
- Handle payment response