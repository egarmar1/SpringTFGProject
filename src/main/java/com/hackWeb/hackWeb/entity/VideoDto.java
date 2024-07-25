package com.hackWeb.hackWeb.entity;

public class VideoDto {

    private int id;

    private String difficulty;

    private String videoFile;

    private Attack attack;

    private TypeAttack typeAttack;

    private boolean saved;

    private boolean completed;

    public VideoDto(int id, String difficulty, String videoFile, Attack attack, TypeAttack typeAttack, boolean saved, boolean completed) {
        this.id = id;
        this.difficulty = difficulty;
        this.videoFile = videoFile;
        this.attack = attack;
        this.typeAttack = typeAttack;
        this.saved = saved;
        this.completed = completed;
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

    @Override
    public String toString() {
        return "VideoDto{" +
                "id=" + id +
                ", difficulty='" + difficulty + '\'' +
                ", video_file='" + videoFile + '\'' +
                ", attack=" + attack.getTitle() +
                ", typeAttack=" + typeAttack +
                ", saved=" + saved +
                ", completed=" + completed +
                '}';
    }
}

