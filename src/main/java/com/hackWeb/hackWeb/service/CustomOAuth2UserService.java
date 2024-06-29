package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final GithubService githubService;
    public CustomOAuth2UserService(UserRepository userRepository, GithubService githubService) {
        this.userRepository = userRepository;
        this.githubService = githubService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        System.out.println("CustomOAuth2UserService: loadUser called");

        String accessToken = userRequest.getAccessToken().getTokenValue();

        String email;
        try {
            email = githubService.getPrimaryEmail(accessToken);
        } catch (URISyntaxException e) {
            throw new OAuth2AuthenticationException("Error while fetching email from GitHub");
        }

        if (email != null) {
            attributes.put("email", email);
        } else {
            throw new OAuth2AuthenticationException("No primary email found for the user");
        }

        System.out.println("El email recogido de github es: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().getName()));

        return new DefaultOAuth2User(
                authorities,
                attributes,
                "email"
        );
    }
}
