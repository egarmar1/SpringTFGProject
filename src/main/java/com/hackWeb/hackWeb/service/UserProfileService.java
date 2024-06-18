package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.UserProfile;
import com.hackWeb.hackWeb.repository.UserProfileRepository;
import com.hackWeb.hackWeb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;


    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }


    public UserProfile getCurrentProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();

            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return user.getUserProfile();

        }
        return  null;
    }

    @Transactional
    public void update(UserProfile currentProfile) {

        System.out.println("Vamos a guardar el user_ " + currentProfile);
        userProfileRepository.save(currentProfile);
    }
}
