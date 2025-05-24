package com.example.yourBedSpace.services.Beds.entity;

import com.example.yourBedSpace.services.UserProfile.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bedspaces")
@NoArgsConstructor
@AllArgsConstructor
public class BedSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String location;
    private String price;
    private String description;
    private String whatsappNumber;
    private boolean isStillAvailable;

    @ManyToOne
    private User owner;

}




