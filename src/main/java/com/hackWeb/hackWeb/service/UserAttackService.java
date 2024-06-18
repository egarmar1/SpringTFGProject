package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.Attack;
import com.hackWeb.hackWeb.entity.User;
import com.hackWeb.hackWeb.entity.UserAttack;
import com.hackWeb.hackWeb.repository.AttackRepository;
import com.hackWeb.hackWeb.repository.UserAttackRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAttackService {

    private final UserAttackRepository userAttackRepository;
    private final AttackRepository attackRepository;
    public UserAttackService(UserAttackRepository userAttackRepository, AttackRepository attackRepository) {
        this.userAttackRepository = userAttackRepository;
        this.attackRepository = attackRepository;
    }

    public void setSave(int attackId, User user, boolean save) {

        Attack attack = attackRepository.findById(attackId).orElseThrow(() -> new RuntimeException("The attack could not be found"));

        UserAttack userAttack = userAttackRepository.findByUserIdAndAttackId(user.getId(),attackId).orElse(new UserAttack());

        userAttack.setAttack(attack);
        userAttack.setUser(user);
        userAttack.setSaved(save);

        userAttackRepository.save(userAttack);
    }

    public void setComplete(int attackId, User user, boolean complete) {
        Attack attack = attackRepository.findById(attackId).orElseThrow(() -> new RuntimeException("The attack could not be found"));

        UserAttack userAttack = userAttackRepository.findByUserIdAndAttackId(user.getId(),attackId).orElse(new UserAttack());

        userAttack.setAttack(attack);
        userAttack.setUser(user);
        userAttack.setCompleted(complete);

        userAttackRepository.save(userAttack);
    }
}
