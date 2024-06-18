package com.hackWeb.hackWeb.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserAttackId implements Serializable {

    private int userId;
    private int attackId;

    public UserAttackId() {
    }

    public UserAttackId(int userId, int attackId) {
        this.userId = userId;
        this.attackId = attackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttackId() {
        return attackId;
    }

    public void setAttackId(int attackId) {
        this.attackId = attackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAttackId that = (UserAttackId) o;
        return userId == that.userId && attackId == that.attackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, attackId);
    }
}
