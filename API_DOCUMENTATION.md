# API Documentation

This document provides a detailed overview of the RESTful APIs exposed by the **Minimal E-Commerce Backend API**.
The APIs follow REST principles with clear request and response structures.

---

## Base URL

```
http://localhost:8080
```

---

## General Conventions

- JSON request and response format
- Standard HTTP status codes
- Clear error messages
- RESTful endpoint naming

---

## User APIs

### Create User
- POST `/users`

### Get User
- GET `/users/{userId}`

---

## Product APIs

### Create Product
- POST `/products`

### Get All Products
- GET `/products`

### Get Product by ID
- GET `/products/{productId}`

---

## Cart APIs

### Add Item
- POST `/cart/add`

### View Cart
- GET `/cart`

### Remove Item
- DELETE `/cart/remove`

---

## Order APIs

### Create Order
- POST `/orders`

### Get Order
- GET `/orders/{orderId}`

---

## Payment APIs

### Create Payment
- POST `/payments/create`

### Payment Webhook
- POST `/payments/webhook`

---

## Notes

- Payment flow is mocked
- Authentication intentionally excluded
