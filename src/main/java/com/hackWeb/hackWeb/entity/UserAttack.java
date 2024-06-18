package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;
import org.aspectj.weaver.bcel.AtAjAttributes;

import java.io.Serializable;

@Entity
@Table(name = "user_attack")
public class UserAttack  implements Serializable {

    @EmbeddedId
    private UserAttackId userAttackId;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("attackId")
    private Attack attack;

    private boolean saved;

    private boolean completed;

    public UserAttack() {
        this.completed = false;
        this.userAttackId = new UserAttackId();
    }

    public UserAttack(UserAttackId userAttackId, User user, Attack attack, boolean saved, boolean completed) {
        this.userAttackId = userAttackId;
        this.user = user;
        this.attack = attack;
        this.saved = saved;
        this.completed = completed;
    }

    public UserAttackId getUserAttackId() {
        return userAttackId;
    }

    public void setUserAttackId(UserAttackId userAttackId) {
        this.userAttackId = userAttackId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userAttackId.setUserId(user.getId());
    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
        this.userAttackId.setAttackId(attack.getId());
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "UserAttack{" +
                ", user=" + user +
                ", attack=" + attack +
                ", saved=" + saved +
                ", completed=" + completed +
                '}';
    }
}
