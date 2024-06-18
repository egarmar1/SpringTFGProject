package com.hackWeb.hackWeb.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_video")
public class UserVideo {


    @EmbeddedId
    private UserVideoId userVideoId;

    @ManyToOne
    @MapsId("videoId")
    private Video video;

    @ManyToOne
    @MapsId("userId")
    private User user;
    private boolean saved;

    private boolean completed;

    public UserVideo() {
        this.completed = false;
        this.userVideoId = new UserVideoId();
    }

    public UserVideo(UserVideoId userVideoId, Video video, User user, boolean saved, boolean completed) {
        this.userVideoId = userVideoId;
        this.video = video;
        this.user = user;
        this.saved = saved;
        this.completed = completed;
    }

    public UserVideoId getUserVideoId() {
        return userVideoId;
    }

    public void setUserVideoId(UserVideoId userVideoId) {
        this.userVideoId = userVideoId;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
