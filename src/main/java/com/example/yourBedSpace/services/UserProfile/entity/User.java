package com.example.yourBedSpace.services.UserProfile.entity;

import com.example.yourBedSpace.services.Beds.entity.BedSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password; // Hashed using BCrypt

    @OneToMany(mappedBy = "owner")
    @JsonIgnore // prevents infinite loop during JSON serialization
    private List<BedSpace> bedSpaces;
    }


