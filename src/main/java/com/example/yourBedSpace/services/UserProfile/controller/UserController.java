package com.example.yourBedSpace.services.UserProfile.controller;

import com.example.yourBedSpace.services.UserProfile.entity.User;
import com.example.yourBedSpace.services.UserProfile.model.LoginRequest;
import com.example.yourBedSpace.services.UserProfile.model.UserRequest;
import com.example.yourBedSpace.services.UserProfile.repository.UserRepository;
import com.example.yourBedSpace.services.UserProfile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


        public UserController(UserService userService) {
            this.userService = userService;
        }

        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
            return userService.registerUser(userRequest);
        }

        @PostMapping("/login")
        public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
            return userService.loginUser(request);
        }

        @PutMapping("/update-password")
        public ResponseEntity<String> updatePassword(@RequestParam String email,
                                                     @RequestParam String oldPassword,
                                                     @RequestParam String newPassword) {
            return userService.updatePassword(email, oldPassword, newPassword);
        }

        @PutMapping("/edit-profile")
        public ResponseEntity<String> editUserDetails(@RequestBody UserRequest userRequest) {
            return userService.editUserDetails(userRequest);
        }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}


