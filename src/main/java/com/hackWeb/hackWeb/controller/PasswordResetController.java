package com.hackWeb.hackWeb.controller;

import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.service.PasswordResetTokenService;
import com.hackWeb.hackWeb.service.UserService;
import com.hackWeb.hackWeb.util.PasswordValidator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordResetController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    private JavaMailSender javaMailSender;

    public PasswordResetController(UserService userService, PasswordResetTokenService passwordResetTokenService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email, Model model){
        String token = UUID.randomUUID().toString();

        Optional<User> user = userService.findByEmail(email);
        if(user.isEmpty()){
            model.addAttribute("error", "El email introducido no está registrado");
            return "forgot-password";
        }

        passwordResetTokenService.createPasswordResetToken(token,user.get());

        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("Clicka el enlace siguiente para cambiar la contraseña:\n" + resetLink);

        javaMailSender.send(mailMessage);

        model.addAttribute("message","Se ha enviado un link a tu email");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam("token") String token, Model model){

        User user = passwordResetTokenService.getUserByResetToken(token);

        if(user == null){
            model.addAttribute("error", "Token inválido o expirado");
            return "reset-password";
        }

        model.addAttribute("token",token);
        return "reset-password";

    }


    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       @RequestParam("token") String token,
                                       Model model){


        if(!password.equals(confirmPassword)){
            model.addAttribute("message", "Las contraseñas no coinciden");
            model.addAttribute("token",token);
            return "reset-password";
        }
        String passwordValidateError = PasswordValidator.validatePassword(password);

        if(passwordValidateError!=null){
            model.addAttribute("message", passwordValidateError);
            model.addAttribute("token",token);
            return "reset-password";
        }
        User user = passwordResetTokenService.getUserByResetToken(token);

        if(user != null){
            userService.changePasswordAndDeleteToken(user,password, token);
            model.addAttribute("message", "Contraseña cambiada exitosamente");
            return "login";
        }else {
            model.addAttribute("error", "Token inválido o expirado");
            return "reset-password";
        }





    }

}
