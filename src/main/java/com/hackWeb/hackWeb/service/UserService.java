package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.UserProfile;
import com.hackWeb.hackWeb.entity.UserType;
import com.hackWeb.hackWeb.repository.UserProfileRepository;
import com.hackWeb.hackWeb.repository.UserRepository;
import com.hackWeb.hackWeb.repository.UserTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, PasswordResetTokenService passwordResetTokenService) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @Transactional
    public void addNewStudent(User user) {
        UserType userTypeStudent = userTypeRepository.findByName("Student");
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(userTypeStudent);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);

        user.setUserProfile(userProfile);

        userRepository.save(user);

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();


            return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found the user: " + username));
        }
        return null;
    }

    @Transactional
    public void changePasswordAndDeleteToken(User user, String password, String token) {
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        passwordResetTokenService.deleteToken(token);
    }
}
