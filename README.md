

# 🛏️ YourBedSpace – Backend API Documentation

### ✅ Base URL

```
http://localhost:8080
```

---

## 🔐 Authentication

### 1. **Register a User**

* **POST** `/auth/register`

```json
Request Body:
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "yourPassword"
}
```

* **Responses**:

  * 200 OK – Registered
  * 409 Conflict – Email already in use

---

### 2. **Login**

* **POST** `/auth/login`

```json
Request Body:
{
  "email": "john@example.com",
  "password": "yourPassword"
}
```

* **Responses**:

  * 200 OK – Login successful
  * 401 Unauthorized – Invalid credentials

---

## 👤 User Management

### 3. **Update Password**

* **PUT** `/auth/update-password`

**Request Params**:

```
email: user@example.com  
oldPassword: oldpass  
newPassword: newpass
```

* **Responses**:

  * 200 OK – Password updated
  * 401 Unauthorized – Old password mismatch

---

### 4. **Edit Profile**

* **PUT** `/auth/edit-profile`

```json
Request Body:
{
  "id": 1,
  "name": "New Name",
  "email": "newemail@example.com"
}
```

* **Validations**:

  * Cannot update to an email already used by another user.

---

## 🛏️ BedSpace Management

### 5. **Create BedSpace**

* **POST** `/api/bedspaces/addBed`

```json
Request Body:
{
  "name": "Room A1",
  "location": "City Center",
  "price": 5000,
  "description": "Spacious bed space in city center",
  "whatsappNumber": "1234567890",
  "stillAvailable": true,
  "id": 1  // This is the User ID (owner)
}
```

* **Response**:

  * 200 OK – Returns created bedspace details
  * 404 Not Found – Owner not found

---

### 6. **Get All BedSpaces**

* **GET** `/api/bedspaces/all`

---

### 7. **Delete User (and optionally their available BedSpace)**

* **DELETE** `/api/users/{id}`

  * If a user has a **stillAvailable = true** BedSpace → deletes both
  * Otherwise → user not deleted if referenced

---

## 📦 Tech Stack

* **Java 17**
* **Spring Boot 3.x**
* **PostgreSQL**
* **Spring Security (Basic Auth)**
* **BCryptPasswordEncoder**

---

## 🔐 Security

* Basic HTTP Authentication is used.
* All endpoints except `/auth/**` and `GET /bedspaces/**` require authentication.
* Example:
  Use **Authorization header** in requests:

  ```
  Authorization: Basic base64encoded(username:password)
  ```

---

## 🔧 Endpoints Summary

| Method | Endpoint                | Description                      | Auth Required |
| ------ | ----------------------- | -------------------------------- | ------------- |
| POST   | `/auth/register`        | Register new user                | ❌ No          |
| POST   | `/auth/login`           | Login user                       | ❌ No          |
| PUT    | `/auth/update-password` | Update password                  | ✅ Yes         |
| PUT    | `/auth/edit-profile`    | Update name/email                | ✅ Yes         |
| POST   | `/api/bedspaces/addBed` | Create new bedspace              | ✅ Yes         |
| GET    | `/api/bedspaces/all`    | View all bedspaces               | ❌ No          |
| DELETE | `/api/users/{id}`       | Delete user + available bedspace | ✅ Yes         |


