package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.service.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordController {

    private final UserService userService;

    private JavaMailSender javaMailSender;

    public PasswordController(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(){
        return "forgot-password";
    }
}
