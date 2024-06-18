package com.hackWeb.hackWeb.entity;

import java.util.Date;

public class AttackDto {

    private Long totalStudentsCompleted;

    private Integer id;

    private String title;

    private String difficulty;

    private TypeAttack typeAttack;

    private boolean saved;

    private boolean completed;

    private String description;
    private Date postedDate;

    private String preVideoFile;
    private String solutionVideoFile;

    public AttackDto(Long totalStudentsCompleted, Integer id, String title, String difficulty, TypeAttack typeAttack, boolean saved, boolean completed, String description, Date postedDate, String preVideoFile, String solutionVideoFile) {
        this.totalStudentsCompleted = totalStudentsCompleted;
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.typeAttack = typeAttack;
        this.saved = saved;
        this.completed = completed;
        this.description = description;
        this.postedDate = postedDate;
        this.preVideoFile = preVideoFile;
        this.solutionVideoFile = solutionVideoFile;
    }


    public Long getTotalStudentsCompleted() {
        return totalStudentsCompleted;
    }

    public void setTotalStudentsCompleted(Long totalStudentsCompleted) {
        this.totalStudentsCompleted = totalStudentsCompleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreVideoFile() {
        return preVideoFile;
    }

    public void setPreVideoFile(String preVideoFile) {
        this.preVideoFile = preVideoFile;
    }

    @Override
    public String toString() {
        return "AttackDto{" +
                "totalStudentsCompleted=" + totalStudentsCompleted +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", typeAttack=" + typeAttack +
                ", saved=" + saved +
                ", completed=" + completed +
                '}';
    }

    public String getSolutionVideoFile() {
        return solutionVideoFile;
    }

    public void setSolutionVideoFile(String solutionVideoFile) {
        this.solutionVideoFile = solutionVideoFile;
    }
}
