package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotEmpty
    private String title;
    @NotEmpty
    private String difficulty;

    @Length(max = 10000)
    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyy")
    private Date posted_date;

    @NotNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "type_attack_id")
    private TypeAttack typeAttack;


    @OneToMany(mappedBy = "attack", cascade = CascadeType.ALL, orphanRemoval = true)
    //// De esta manera si eliminamos un video de la lista se eliminara de la base de datos;
    private List<Video> videos;

    @Size(max = 255)
    private String question;
    @Size(max = 255)

    private String answer;

    @NotEmpty(message = "Tienes que introducir una imagen de docker")
    @Column(unique = true)
    private String dockerImageName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "attack")
    private List<UserAttack> userAttacks;

    public Attack() {

    }

    public Attack(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Attack(int id, String title, String difficulty, String description, Date postedDate, TypeAttack typeAttack, List<Video> videos, String question, String answer, List<UserAttack> userAttacks, String dockerImageName) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.description = description;
        this.posted_date = postedDate;
        this.typeAttack = typeAttack;
        this.videos = videos;
        this.question = question;
        this.answer = answer;
        this.userAttacks = userAttacks;
        this.dockerImageName = dockerImageName;
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


    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDockerImageName() {
        return dockerImageName;
    }

    public void setDockerImageName(String dockerImageName) {
        this.dockerImageName = dockerImageName;
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

}
