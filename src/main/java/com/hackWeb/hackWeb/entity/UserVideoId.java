package com.hackWeb.hackWeb.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserVideoId implements Serializable {

    private int userId;

    private int videoId;


    public UserVideoId() {
    }

    public UserVideoId(int userId, int videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVideoId that = (UserVideoId) o;
        return userId == that.userId && videoId == that.videoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, videoId);
    }
}
