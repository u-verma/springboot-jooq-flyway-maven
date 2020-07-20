package com.diamond.controller;

import com.diamond.domain.UserProfile;
import com.diamond.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserRegistration {

    private final UserProfileService userProfileService;

    public UserRegistration(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/fetch/{id}")
    public UserProfile fetchUserProfile(@PathVariable String id) {
        return userProfileService.fetchUserProfile(id);
    }

    @PutMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody UserProfile userProfile) {
        String response = userProfileService.createUserProfile(userProfile);
        URI location = URI.create("http://localhost:8081/api/user/fetch/" + response);
        return ResponseEntity.created(location).body(response);
    }
}
