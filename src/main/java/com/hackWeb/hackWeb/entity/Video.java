package com.hackWeb.hackWeb.entity;

import com.hackWeb.hackWeb.entity.enums.VideoType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "video")
public class Video {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String difficulty;
    private String videoFile;

    @Enumerated(EnumType.STRING)
    private VideoType type;

    @OneToOne
    @JoinColumn(name = "attack_id")
    private Attack attack;

    @ManyToOne
    @JoinColumn(name = "type_attack_id")
    private TypeAttack typeAttack;


    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserVideo> userVideos;

    public Video() {
    }
    public Video(Attack attack, VideoType type) {
        this.attack = attack;
        this.type = type;
    }

    public Video(int id, String difficulty, String videoFile, TypeAttack typeAttack, VideoType type, Attack attack, TypeAttack typeAttack1, List<UserVideo> userVideos) {
        this.id = id;
        this.difficulty = difficulty;
        this.videoFile = videoFile;
        this.type = type;
        this.attack = attack;
        this.typeAttack = typeAttack1;
        this.userVideos = userVideos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", difficulty='" + difficulty + '\'' +
                ", videoFile='" + videoFile + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public TypeAttack getTypeAttack() {
        return typeAttack;
    }

    public void setTypeAttack(TypeAttack typeAttack) {
        this.typeAttack = typeAttack;
    }

    public VideoType getType() {
        return type;
    }

    public void setType(VideoType type) {
        this.type = type;
    }


    public List<UserVideo> getUserVideos() {
        return userVideos;
    }

    public void setUserVideos(List<UserVideo> userVideos) {
        this.userVideos = userVideos;
    }
}
