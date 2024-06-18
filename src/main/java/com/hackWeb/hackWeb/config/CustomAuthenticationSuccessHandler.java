package com.hackWeb.hackWeb.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//porque el CustomAuthenticationSuccessHandler es un component, y no un configuration o un bean o cualquier otra cosa ...
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        boolean hasStudentRole = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Student"));
        boolean hasAdminRole = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin"));

        if (hasStudentRole || hasAdminRole) {
            response.sendRedirect("/dashboard/");
        }
    }
}
