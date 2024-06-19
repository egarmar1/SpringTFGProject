package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "attack")
public class Attack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String difficulty;

    @Length(max = 10000)
    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyy")
    private Date posted_date;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "type_attack_id")
    private TypeAttack typeAttack;


    @OneToMany(mappedBy = "attack", cascade = CascadeType.ALL, orphanRemoval = true) //// De esta manera si eliminamos un video de la lista se eliminara de la base de datos;
    private List<Video> videos;

    private String laboratoryUrl;




    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "attack")
    private List<UserAttack> userAttacks;

    public Attack() {

    }

    public Attack(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Attack(int id, String title, String difficulty, String description, Date postedDate, TypeAttack typeAttack, List<Video> videos, String laboratoryUrl, List<UserAttack> userAttacks) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.description = description;
        this.posted_date = postedDate;
        this.typeAttack = typeAttack;
        this.videos = videos;
        this.laboratoryUrl = laboratoryUrl;
        this.userAttacks = userAttacks;
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

    public TypeAttack getTypeAttack() {
        return typeAttack;
    }

    public void setTypeAttack(TypeAttack typeAttack) {
        this.typeAttack = typeAttack;
    }


    public List<UserAttack> getUserAttacks() {
        return userAttacks;
    }

    public void setUserAttacks(List<UserAttack> userAttacks) {
        this.userAttacks = userAttacks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(Date posted_date) {
        this.posted_date = posted_date;
    }
    @Override
    public String toString() {
        return "Attack{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", description='" + description + '\'' +
                ", posted_date=" + posted_date +
                ", typeAttack=" + typeAttack +
                '}';
    }


    public String getLaboratoryUrl() {
        return laboratoryUrl;
    }

    public void setLaboratoryUrl(String laboratoryUrl) {
        this.laboratoryUrl = laboratoryUrl;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
