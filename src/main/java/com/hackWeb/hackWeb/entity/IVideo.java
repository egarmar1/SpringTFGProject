package com.hackWeb.hackWeb.entity;

public interface IVideo {

    int getId();

    String getTitle();

    String getDifficulty();

    String getVideoFile();
    String getType();
    Integer getAttackId();

    Integer getTypeAttackId();
    String getTypeAttackName();

    Boolean getIsSaved();
    Boolean getIsCompleted();

}
