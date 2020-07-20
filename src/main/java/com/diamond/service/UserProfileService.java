package com.diamond.service;

import com.diamond.domain.UserId;
import com.diamond.domain.UserProfile;
import com.diamond.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile fetchUserProfile(String userId){
        return userProfileRepository.fetch(userId);
    }


    public String createUserProfile(UserProfile userProfile){
        userProfile.setUserId(UserId.builder().value(UUID.randomUUID()).build());
        userProfileRepository.save(userProfile);
        return userProfile.getUserId().getValue().toString();
    }

}
