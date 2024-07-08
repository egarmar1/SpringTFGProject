package com.hackWeb.hackWeb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "container_info")
public class ContainerInfo {

    private static final int EXPIRATION = 1; // minutos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String containerId;

    private Integer webSockifyPort;

    private int containerPort;
    @OneToOne()
    @JoinColumn(name = "user_id" )
    private User user;
    @OneToOne()
    @JoinColumn(name = "attack_id")
    private Attack attack;

    private LocalDateTime expiryDate;

    public ContainerInfo() {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public ContainerInfo(int id, String containerId, Integer webSockifyPort, int containerPort, User user, Attack attack) {
        this.id = id;
        this.containerId = containerId;
        this.webSockifyPort = webSockifyPort;
        this.containerPort = containerPort;
        this.user = user;
        this.attack = attack;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Integer getWebSockifyPort() {
        return webSockifyPort;
    }

    public void setWebSockifyPort(Integer webSockifyPort) {
        this.webSockifyPort = webSockifyPort;
    }


    public int getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(int containerPort) {
        this.containerPort = containerPort;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    @Override
    public String toString() {
        return "ContainerInfo{" +
                "id=" + id +
                ", containerId='" + containerId + '\'' +
                ", webSockifyPort=" + webSockifyPort +
                ", containerPort=" + containerPort +
                ", user=" + user +
                ", attack=" + attack +
                '}';
    }




    private LocalDateTime calculateExpiryDate(int expiration){
        return LocalDateTime.now().plusMinutes(expiration);
    }
}
