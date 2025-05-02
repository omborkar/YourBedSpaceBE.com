package com.example.yourBedSpace.services.Beds.repository;

import com.example.yourBedSpace.services.Beds.entity.BedSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedSpaceRepository extends JpaRepository<BedSpace, Long> {
    List<BedSpace> findByIsStillAvailableTrue();
    List<BedSpace> findByOwnerId(Long ownerId);
}


