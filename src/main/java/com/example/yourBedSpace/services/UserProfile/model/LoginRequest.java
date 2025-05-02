package com.example.yourBedSpace.services.UserProfile.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
