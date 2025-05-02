package com.example.yourBedSpace.services.Beds.controller;

import com.example.yourBedSpace.services.Beds.entity.BedSpace;
import com.example.yourBedSpace.services.Beds.model.BedSpaceReqRes;
import com.example.yourBedSpace.services.Beds.service.BedSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/bedspaces")
@RequiredArgsConstructor
public class BedSpaceController {

    private final BedSpaceService bedSpaceService;

    @PostMapping
    public ResponseEntity<?> addBedSpace(@RequestBody BedSpaceReqRes request) {
        BedSpace created = bedSpaceService.createBedSpace(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedSpaceReqRes> getBedSpace(@PathVariable Long id) {
        return ResponseEntity.ok(bedSpaceService.getBedSpace(id));
    }

    @PutMapping("/updateBedDetails")
    public ResponseEntity<BedSpaceReqRes> updateBedSpace(@RequestBody BedSpaceReqRes request) {
        return ResponseEntity.ok(bedSpaceService.updateBedSpace(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBedSpace(@PathVariable Long id) {
        bedSpaceService.deleteBedSpace(id);
        return ResponseEntity.ok("BedSpace deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<BedSpaceReqRes>> getAllBedSpaces() {
        return ResponseEntity.ok(bedSpaceService.getAllBedSpaces());
    }

    @PatchMapping("/{id}/mark-sold")
    public ResponseEntity<String> markBedSpaceAsSold(@PathVariable Long id, @RequestParam Long requesterUserId) {
        return ResponseEntity.ok(bedSpaceService.markAsSold(id, requesterUserId));
    }
}