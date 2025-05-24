
---

# API Documentation

---

## BedSpace API

Base URL: `/api/bedspaces`

| HTTP Method | Endpoint            | Description                     | Request Body                     | Response                  |
| ----------- | ------------------- | ------------------------------- | -------------------------------- | ------------------------- |
| POST        | `/addBed`           | Create a new BedSpace           | `BedSpaceReqRes` JSON            | Created `BedSpace` entity |
| GET         | `/{id}`             | Get details of a BedSpace by ID | None                             | `BedSpaceReqRes`          |
| PUT         | `/updateBedDetails` | Update BedSpace details         | `BedSpaceReqRes` JSON            | Updated `BedSpaceReqRes`  |
| DELETE      | `/{id}`             | Delete a BedSpace by ID         | None                             | Success message string    |
| GET         | `/all`              | Get list of all BedSpaces       | None                             | List of `BedSpaceReqRes`  |
| PATCH       | `/{id}/mark-sold`   | Mark a BedSpace as sold         | Request param: `requesterUserId` | Success message string    |

### Example:

**Add BedSpace:**

```
POST /api/bedspaces/addBed
Content-Type: application/json

{
  "field1": "value1",
  "field2": "value2"
}
```

---

## User API

Base URL: `/api/users`

| HTTP Method | Endpoint           | Description               | Request Body        | Request Params                                       | Response                     |
| ----------- | ------------------ | ------------------------- | ------------------- | ---------------------------------------------------- | ---------------------------- |
| POST        | `/register`        | Register a new user       | `UserRequest` JSON  | None                                                 | Success message string       |
| POST        | `/login`           | Login a user              | `LoginRequest` JSON | None                                                 | Success message string/token |
| PUT         | `/update-password` | Update user password      | None                | `email`, `oldPassword`, `newPassword` (all required) | Success message string       |
| PUT         | `/edit-profile`    | Edit user profile details | `UserRequest` JSON  | None                                                 | Success message string       |
| GET         | `/users/{id}`      | Get user details by ID    | None                | None                                                 | User details / object        |
| GET         | `/users`           | Get list of all users     | None                | None                                                 | List of `User` entities      |
| DELETE      | `/users/{id}`      | Delete a user by ID       | None                | None                                                 | Success message string       |

### Example:

**Update Password:**

```
PUT /api/users/update-password?email=user@example.com&oldPassword=old123&newPassword=new123
```

**Edit Profile:**

```
PUT /api/users/edit-profile
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "otherFields": "..."
}
```

---

