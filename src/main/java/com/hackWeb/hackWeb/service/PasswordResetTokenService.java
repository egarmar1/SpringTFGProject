package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.PasswordResetToken;
import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public void createPasswordResetToken(String token, User user) {
        PasswordResetToken theToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(theToken);
    }

    public User getUserByResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null){
            return null;
        }
        User user = passwordResetToken.getUser();

        if(passwordResetToken.getExpiryDate().before(new Date())){
            return null;
        }
        return user != null ? passwordResetToken.getUser() : null;
    }

    public void deleteToken(String token) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(token);
        passwordResetTokenRepository.delete(passwordToken);
    }
}
