package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.IVideo;
import com.hackWeb.hackWeb.entity.UserVideo;
import com.hackWeb.hackWeb.entity.UserVideoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserVideoRepository extends JpaRepository<UserVideo, UserVideoId> {

    Optional<UserVideo> findByUserIdAndVideoId(int userId, int videoId);


    @Query(value = "SELECT v.id, v.title, v.difficulty, v.video_file, a.id AS attack_id, " +
            "ta.id AS type_attack_id, ta.name AS type_attack_name, " +
            "uv.saved as is_saved, uv.completed as is_completed " +
            "FROM video v LEFT JOIN attack a ON a.id = v.attack_id " +
            "INNER JOIN type_attack ta ON ta.id = v.type_attack_id " +
            "INNER JOIN user_video uv ON uv.video_id = v.id " +
            "WHERE uv.user_id = :userId " +
            "AND uv.video_id = :videoId", nativeQuery = true)
    IVideo getOneByVideoIdAndUserId(@Param("videoId") int videoId,
                                    @Param("userId") int userId);
}
