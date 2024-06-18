package com.hackWeb.hackWeb.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "type_attack")
public class TypeAttack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    @OneToMany(targetEntity = Attack.class, cascade = CascadeType.ALL, mappedBy = "typeAttack") // El mappedBy referencia al de la entidad
    private List<Attack> attacks;

    @OneToMany(mappedBy = "typeAttack")
    private List<Video> videos;

    public TypeAttack() {
    }

    public TypeAttack(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public TypeAttack(int id, String name, List<Attack> attacks) {
        this.id = id;
        this.name = name;
        this.attacks = attacks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "TypeAttack{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
