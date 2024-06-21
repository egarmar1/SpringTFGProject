package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.AttackDto;
import com.hackWeb.hackWeb.entity.IAttack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttackRepository extends JpaRepository<Attack, Integer> {

    @Query(value = "SELECT a.* FROM attack a " +
            "INNER JOIN type_attack t ON a.type_attack_id=t.id " +
            "WHERE a.difficulty IN(:difficulties) " +
            "AND t.name IN(:nameAttacks) " +
            "AND a.title LIKE %:attackTitle%",
            nativeQuery = true)
    List<Attack> search(@Param("difficulties") List<String> difficulties,
                        @Param("nameAttacks") List<String> typeAttacks,
                        @Param("attackTitle") String attackTitle);


    @Query(value = "SELECT a.id, COUNT(ua.user_id) AS total_students_completed, a.title, a.difficulty, ta.id AS type_attack_id, ta.name AS type_attack_name, " +
            "MAX(CASE WHEN ua.user_id = :userId THEN ua.saved ELSE 0 END) AS is_saved, " +
            "MAX(CASE WHEN ua.user_id = :userId THEN ua.completed ELSE 0 END) AS is_completed " +
            "FROM attack a " +
            "LEFT JOIN user_attack ua " +
            "ON a.id = ua.attack_id " +
            "INNER JOIN type_attack ta " +
            "ON a.type_attack_id = ta.id " +
            "WHERE a.difficulty IN (:difficulties) " +
            "AND ta.name IN (:nameTypeAttacks) " +
            "AND a.title LIKE %:attackTitle% " +
            "GROUP BY a.id", nativeQuery = true)
    List<IAttack> searchDto(@Param("difficulties") List<String> difficulties,
                            @Param("nameTypeAttacks") List<String> typeAttacks,
                            @Param("attackTitle") String attackTitle,
                            @Param("userId") int userId);
//
//    @Query(value = "SELECT a.id, COUNT(ua.user_id) AS total_students_completed, a.title, a.difficulty, a.posted_date, a.description, a.pre_video_file, a.solution_video_file, ta.id AS type_attack_id, ta.name AS type_attack_name, " +
//            "MAX(CASE WHEN ua.user_id = :userId THEN ua.saved ELSE 0 END) AS is_saved, " +
//            "MAX(CASE WHEN ua.user_id = :userId THEN ua.completed ELSE 0 END) AS is_completed " +
//            "FROM attack a " +
//            "LEFT JOIN user_attack ua " +
//            "ON a.id = ua.attack_id " +
//            "INNER JOIN type_attack ta " +
//            "ON a.type_attack_id = ta.id " +
//            "WHERE a.id = :attackId  " +
//            "GROUP BY a.id", nativeQuery = true)
    @Query(value = "SELECT a.id, COUNT(ua.user_id) AS total_students_completed, a.title, a.difficulty, a.posted_date, a.description, a.laboratory_url, a.question, a.answer, pre_video.video_file AS pre_video_file, solution_video.video_file AS solution_video_file, ta.id AS type_attack_id, ta.name AS type_attack_name, " +
            "MAX(CASE WHEN ua.user_id = :userId THEN ua.saved ELSE 0 END) AS is_saved, " +
            "MAX(CASE WHEN ua.user_id = :userId THEN ua.completed ELSE 0 END) AS is_completed " +
            "FROM attack a LEFT JOIN user_attack ua ON a.id = ua.attack_id " +
            "INNER JOIN type_attack ta ON a.type_attack_id = ta.id " +
            "LEFT JOIN video pre_video ON a.id = pre_video.attack_id AND pre_video.type = 'PRE'" +
            "LEFT JOIN video solution_video ON a.id = solution_video.attack_id AND solution_video.type = 'SOLUTION'" +
            "WHERE a.id = :attackId " +
            "GROUP BY a.id, a.title, a.difficulty, a.posted_date, a.description, a.laboratory_url, pre_video.video_file, solution_video.video_file, ta.id, ta.name;",
            nativeQuery = true)
    IAttack getOneDtoById(@Param("attackId") int attackId,
                          @Param("userId") int userId);

//    @Query(value = "UPDATE user_attack " +
//            "SET saved = 0 " +
//            "WHERE user_id = :userId " +
//            "AND attack_id = :attackId")
//    void savedAttackToUser(@Param("attackId") int attackId,
//                           @Param("userId") int userId);
}
