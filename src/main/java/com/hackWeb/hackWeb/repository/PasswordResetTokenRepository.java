package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Integer> {

    PasswordResetToken findByToken(String token);
}
