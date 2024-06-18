package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;

import java.lang.reflect.Type;

@Entity
@Table(name = "video")
public class Video {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String title;

    private String difficulty;
    private String video_file;

    @OneToOne
    @JoinColumn(name = "attack_id")
    private Attack attack;

    @ManyToOne()
    @JoinColumn(name = "type_attack_id")
    private TypeAttack typeAttack;

    public Video() {
    }

    public Video(int id, String title, String difficulty, String video_file, TypeAttack typeAttack, Attack attack, TypeAttack typeAttack1) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.video_file = video_file;
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

    public String getVideo_file() {
        return video_file;
    }

    public void setVideo_file(String video_file) {
        this.video_file = video_file;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", video_file='" + video_file + '\'' +
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
}
