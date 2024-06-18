package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.UserVideo;
import com.hackWeb.hackWeb.entity.UserVideoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVideoRepository extends JpaRepository<UserVideo, UserVideoId> {

    Optional<UserVideo> findByUserIdAndVideoId(int userId, int videoId);
}
