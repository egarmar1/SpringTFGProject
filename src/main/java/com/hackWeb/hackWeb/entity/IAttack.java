package com.hackWeb.hackWeb.entity;

import java.util.Date;

//// Con la interfaz cogemos las cosas de la base de datos, para luego convertirlo al Dto
public interface IAttack {

    int getId();

    Long getTotalStudentsCompleted();


    String getTitle();

    String getDifficulty();

    int getTypeAttackId();

    String getTypeAttackName();


    Integer getIsSaved();

    Integer getIsCompleted();

    String getDescription();

    Date getPostedDate();

    String getPreVideoFile();
    String getSolutionVideoFile();
    String getLaboratoryUrl();
    String getQuestion();
    String getAnswer();

}
