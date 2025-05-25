package com.example.yourBedSpace.services.Beds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedSpaceReqRes {
    private Long id;
    private String name;
    private String location;
    private String price;
    private String description;
    private String whatsappNumber;
    private boolean isStillAvailable;
    private Long bedOwnerId;
}
