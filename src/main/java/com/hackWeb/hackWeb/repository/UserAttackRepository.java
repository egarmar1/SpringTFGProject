package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.UserAttack;
import com.hackWeb.hackWeb.entity.UserAttackId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAttackRepository extends JpaRepository<UserAttack, UserAttackId> {
    Optional<UserAttack> findByUserIdAndAttackId(int userId, int attackId);
}
