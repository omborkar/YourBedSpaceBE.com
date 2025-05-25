package com.example.yourBedSpace.services.Beds.service;


import com.example.yourBedSpace.services.Beds.entity.BedSpace;
import com.example.yourBedSpace.services.Beds.model.BedSpaceReqRes;
import com.example.yourBedSpace.services.Beds.repository.BedSpaceRepository;
import com.example.yourBedSpace.services.UserProfile.entity.User;
import com.example.yourBedSpace.services.UserProfile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BedSpaceService {

    private final BedSpaceRepository bedSpaceRepository;
    private final UserRepository userRepository;

    public BedSpace createBedSpace(BedSpaceReqRes request) {
        User owner = userRepository.findById(request.getBedOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        BedSpace bedSpace = new BedSpace();
        bedSpace.setName(request.getName());
        bedSpace.setLocation(request.getLocation());
        bedSpace.setPrice(request.getPrice());
        bedSpace.setDescription(request.getDescription());
        bedSpace.setWhatsappNumber(request.getWhatsappNumber());
        bedSpace.setStillAvailable(request.isStillAvailable());
        bedSpace.setOwner(owner);

        return bedSpaceRepository.save(bedSpace);
    }

    public BedSpaceReqRes getBedSpace(Long id) {
        BedSpace bedSpace = bedSpaceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BedSpace not found"));
        return mapToResponse(bedSpace);
    }

    public BedSpaceReqRes updateBedSpace(BedSpaceReqRes request) {
        BedSpace bedSpace = bedSpaceRepository.findById(Long.valueOf(request.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BedSpace not found"));

        bedSpace.setName(request.getName());
        bedSpace.setLocation(request.getLocation());
        bedSpace.setPrice(request.getPrice());
        bedSpace.setDescription(request.getDescription());
        bedSpace.setWhatsappNumber(request.getWhatsappNumber());
        bedSpace.setStillAvailable(request.isStillAvailable());

        bedSpace = bedSpaceRepository.save(bedSpace);
        return mapToResponse(bedSpace);
    }

    public void deleteBedSpace(Long id) {
        // Check if bedspace exists, will throw NOT_FOUND if not found
        BedSpace bedSpace = getBedSpaceById(id);

        bedSpaceRepository.deleteById(bedSpace.getId());
    }

    // This method should be defined at the class level, NOT inside another method
    public BedSpace getBedSpaceById(Long id) {
        return bedSpaceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BedSpace not found"));
    }


    public List<BedSpaceReqRes> getAllBedSpaces() {
        return bedSpaceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public String markAsSold(Long id, Long requesterUserId) {
        BedSpace bedSpace = bedSpaceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BedSpace not found"));

        User requester = userRepository.findById(requesterUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Requester not found"));

        boolean isAdmin = "admin@gmail.com".equalsIgnoreCase(requester.getEmail());
        boolean isOwner = bedSpace.getOwner().getId().equals(requesterUserId);

        if (!isAdmin && !isOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to mark as sold");
        }

        bedSpace.setStillAvailable(false);
        bedSpaceRepository.save(bedSpace);
        return "BedSpace marked as sold";
    }

    private BedSpaceReqRes mapToResponse(BedSpace bedSpace) {
        BedSpaceReqRes res = new BedSpaceReqRes();
        res.setId(bedSpace.getId());
        res.setName(bedSpace.getName());
        res.setLocation(bedSpace.getLocation());
        res.setPrice(bedSpace.getPrice());
        res.setDescription(bedSpace.getDescription());
        res.setWhatsappNumber(bedSpace.getWhatsappNumber());
        res.setStillAvailable(bedSpace.isStillAvailable());
        res.setBedOwnerId(bedSpace.getOwner().getId());
        return res;
    }
}
