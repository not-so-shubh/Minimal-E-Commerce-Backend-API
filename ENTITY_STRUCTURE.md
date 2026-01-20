# Entity Structure

Overview of core domain entities and relationships.

---

## User
- id
- name
- email

---

## Product
- id
- name
- price
- stock

---

## CartItem
- product
- quantity

---

## Order
- orderItems
- totalAmount
- status

---

## Payment
- orderId
- amount
- status

---

## Design Notes
- Clean separation of concerns
- MongoDB-friendly structure
- Scalable entity design
