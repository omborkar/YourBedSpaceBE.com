package com.example.yourBedSpace.services.UserProfile.service;

import com.example.yourBedSpace.services.Beds.entity.BedSpace;
import com.example.yourBedSpace.services.Beds.repository.BedSpaceRepository;
import com.example.yourBedSpace.services.UserProfile.entity.User;
import com.example.yourBedSpace.services.UserProfile.model.LoginRequest;
import com.example.yourBedSpace.services.UserProfile.model.UserRequest;
import com.example.yourBedSpace.services.UserProfile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BedSpaceRepository bedSpaceRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> registerUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already in use");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User Registered Successfully!");
    }

    public ResponseEntity<String> loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login Successful!");
        }

        return ResponseEntity.status(401).body("Invalid Credentials");
    }

    public ResponseEntity<String> updatePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(401).body("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok("Password updated successfully");
    }

    public ResponseEntity<String> editUserDetails(UserRequest userRequest) {
        if (userRequest.getId() == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }

        User user = userRepository.findById(userRequest.getId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().equals(user.getEmail())) {
            Optional<User> userWithEmail = userRepository.findByEmail(userRequest.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(user.getId())) {
                return ResponseEntity.status(409).body("Email is already in use by another user");
            }
            user.setEmail(userRequest.getEmail());
        }

        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }

        userRepository.save(user);
        return ResponseEntity.ok("User details updated successfully");
    }


    public ResponseEntity<?> getUserById(Long id) {
        return userRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();
        List<BedSpace> bedSpaces = bedSpaceRepository.findByOwnerId(id);

        boolean hasAvailableBedSpaces = bedSpaces.stream()
                .anyMatch(BedSpace::isStillAvailable);

        if (hasAvailableBedSpaces) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete user: Some BedSpaces are still available");
        }

        // All bedspaces are sold or none exist – delete bedspaces first
        bedSpaceRepository.deleteAll(bedSpaces);
        userRepository.delete(user);

        return ResponseEntity.ok("User and related bedspaces deleted successfully");
    }


}

