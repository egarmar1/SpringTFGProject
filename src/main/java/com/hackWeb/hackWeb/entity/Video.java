package com.hackWeb.hackWeb.entity;

import com.hackWeb.hackWeb.entity.enums.VideoType;
import jakarta.persistence.*;

@Entity
@Table(name = "video")
public class Video {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String title;

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

    public Video() {
    }
    public Video(Attack attack, VideoType type) {
        this.attack = attack;
        this.type = type;
    }

    public Video(int id, String title, String difficulty, String videoFile, TypeAttack typeAttack, VideoType type, Attack attack, TypeAttack typeAttack1) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.videoFile = videoFile;
        this.type = type;
        this.attack = attack;
        this.typeAttack = typeAttack1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                ", title='" + title + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", videoFile='" + videoFile + '\'' +
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
}
