package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
}
